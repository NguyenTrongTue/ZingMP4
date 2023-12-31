using Dapper;
using Microsoft.AspNetCore.Mvc;
using RabbitMQ;
using ZINGMP4.Application.Interface;
using ZINGMP4.Application.Interface.Playlist;
using ZINGMP4.Application.Interface.Song;
using ZINGMP4.Application.Service;
using ZINGMP4.Domain.Cache;
using ZINGMP4.Domain.Interface;
using ZINGMP4.Domain.Interface.Auth;
using ZINGMP4.Domain.MISAException;
using ZINGMP4.Domain.Resource;
using ZINGMP4.Infrastructure.Cache;
using ZINGMP4.Infrastructure.Repository;
using ZINGMP4.Infrastructure.UnitOfWork;
using ZINGMP4.Middleware;

namespace ZingMP4.API
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddCors(p => p.AddPolicy("MyCors", build =>
            {
                build.WithOrigins("*").AllowAnyMethod().AllowAnyHeader();
            }));
            // Add services to the container.

            builder.Services.AddControllers().ConfigureApiBehaviorOptions(options =>
            {
                options.InvalidModelStateResponseFactory = context =>
                {
                    var errors = context.ModelState.Values
                    .SelectMany(x => x.Errors);

                    return new BadRequestObjectResult(Newtonsoft.Json.JsonConvert.SerializeObject(new BaseException()
                    {

                        ErrorCode = 2,
                        UserMessage = Resource.ExceptionValidateDefault,
                        DevMessage = Resource.ExceptionValidateDefault,
                        TraceId = "",
                        MoreInfor = "",
                        Errors = errors
                    })); ;
                };
            }).AddJsonOptions(options =>
            {

                options.JsonSerializerOptions.PropertyNamingPolicy = null;
            });
            DefaultTypeMap.MatchNamesWithUnderscores = true;

            builder.Services.AddAutoMapper(AppDomain.CurrentDomain.GetAssemblies());
            var connectionString = builder.Configuration.GetConnectionString("MP4");
            builder.Services.AddStackExchangeRedisCache(
                options =>
                {
                    options.Configuration = builder.Configuration.GetConnectionString("Redis");
                    options.InstanceName = "CacheMP4_";
                });

            builder.Services.AddScoped<IAuthService, AuthService>();

            builder.Services.AddScoped<IUserRepository, UserRepository>();

            builder.Services.AddScoped<ISongRepository, SongRepository>();

            builder.Services.AddScoped<ISongInterface, SongService>();

            builder.Services.AddScoped<IPlaylistRepository, PlaylistRepository>();

            builder.Services.AddScoped<IPlaylistService, PlaylistService>();

            builder.Services.AddScoped<IUserService, UserService>();
            builder.Services.AddScoped<IRedisCache, RedisCache>();

            builder.Services.AddScoped<IMessagerProvider, MessageProvider>();


            builder.Services.AddScoped<IUnitOfWork>((provider => new UnitOfWork(connectionString)));
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();

            builder.Services.AddSwaggerGen();
            var app = builder.Build();
            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }
            app.UseStaticFiles();
            app.UseCors("MyCors");
            app.UseHttpsRedirection();
            app.UseMiddleware<ExceptionMiddleware>();
            app.UseAuthorization();
            app.MapControllers();
            app.Run();



        }
    }
}