using ZINGMP4.Domain.MISAException;
using ZINGMP4.Domain.Resource;

namespace ZINGMP4.Middleware
{
    public class ExceptionMiddleware
    {
        /// <summary>
        /// Khi một hàm đi qua middleware nó phải đấy sang hàm tiếp theo, nên cần requestdelegate _next;
        /// </summary>
        private readonly RequestDelegate _next;

        public ExceptionMiddleware(RequestDelegate next)
        {
            _next = next;
        }


        /// <summary>
        /// hàm xử lý lỗi khi đi quan middleware
        /// </summary>
        /// <param name="context">context của http</param>
        /// <returns></returns>
        /// Created by: nttue (12/07/2023)
        public async Task Invoke(HttpContext context)
        {
            try
            {
                //nếu đi qua hàm _next mà bị lỗi thì nó nhảy vào catch để bắt Exception
                await _next(context);

            }
            catch (Exception ex)
            {
                await HandleExceptionAsync(context, ex);
            }
        }

        /// <summary>
        /// Hàm xử lý các exception
        /// </summary>
        /// <param name="context">context của http</param>
        /// <param name="exception">ngoại lệ trả về.</param>
        /// <returns>Trả về ngoại lệ</returns>
        /// Created by: nttue (12/07/2023)
        private static async Task HandleExceptionAsync(HttpContext context, Exception exception)
        {
            // ban đầu contentType = null sau đó mình gán lại thành application/json.
            context.Response.ContentType = "application/json";
            Console.WriteLine(exception);

            if (exception is NotFoundException)
            {
                context.Response.StatusCode = StatusCodes.Status404NotFound;
                await context.Response.WriteAsJsonAsync(
                    new BaseException()
                    {
                        // ép kiểu của exception thành kiểu của NotFound exception
                        ErrorCode = 1,
                        UserMessage = Resource.ExceptionNotFoundDefault,
                        DevMessage = Resource.ExceptionNotFoundDefault,
                        TraceId = context.TraceIdentifier,
                        MoreInfor = exception.HelpLink
                    }
                   );
            }
            else if (exception is AuthException)
            {
                context.Response.StatusCode = StatusCodes.Status409Conflict;
                await context.Response.WriteAsJsonAsync(
                   new BaseException()
                   {
                       ErrorCode = 5,
                       UserMessage = exception.Message ?? Resource.ValidateUserInputError,
                       DevMessage = exception.Message ?? Resource.ValidateUserInputError,
                       TraceId = context.TraceIdentifier,
                       MoreInfor = exception.HelpLink
                   }
                   );

            }
            else if (exception is ValidateException)
            {
                context.Response.StatusCode = StatusCodes.Status409Conflict;
                await context.Response.WriteAsJsonAsync(
                   new BaseException()
                   {
                       ErrorCode = 2,
                       UserMessage = exception.Message ?? Resource.ValidateUserInputError,
                       DevMessage = exception.Message ?? Resource.ValidateUserInputError,
                       TraceId = context.TraceIdentifier,
                       MoreInfor = exception.HelpLink
                   }
                   );

            }
            else if (exception is ConflictException)
            {
                context.Response.StatusCode = StatusCodes.Status409Conflict;
                await context.Response.WriteAsJsonAsync(
                   new BaseException()
                   {
                       ErrorCode = 3,
                       UserMessage = exception.Message ?? Resource.ValidateUserInputError,
                       DevMessage = exception.Message ?? Resource.ValidateUserInputError,
                       TraceId = context.TraceIdentifier,
                       MoreInfor = exception.HelpLink
                   }
                   );

            }
            else
            {
                context.Response.StatusCode = StatusCodes.Status500InternalServerError;
                await context.Response.WriteAsJsonAsync(
                     new BaseException()
                     {
                         ErrorCode = 4,
                         UserMessage = Resource.ExceptionSystemDefault,
                         DevMessage = Resource.ExceptionSystemDefault,
                         TraceId = context.TraceIdentifier,
                         MoreInfor = exception.HelpLink
                     }
                   );
            }

        }
    }
}
