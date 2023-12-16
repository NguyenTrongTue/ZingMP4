using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using RabbitMQ;
using ZINGMP4.API.Model;

namespace ZINGMP4.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BookingsController : ControllerBase
    {
        private readonly ILogger<BookingsController> _logger;
        private readonly IMessagerProvider _messagerProvider;

        public static readonly List<Book> _bookings = new List<Book>();

        public BookingsController(ILogger<BookingsController> logger, IMessagerProvider messagerProvider)
        {
            _logger = logger;
            _messagerProvider = messagerProvider;
        }

        [HttpPost]
        public IActionResult CreateBooking(Book newBooking)
        {
            if (!ModelState.IsValid) return BadRequest();

            _bookings.Add(newBooking);
            _messagerProvider.SendingMessage<Book>(newBooking);

            return Ok();


        }

    }
}
