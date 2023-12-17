namespace ZINGMP4.API.Model
{
    public class Book
    {
        public Guid Id { get; set; }

        public string PassengerName { get; set; } = string.Empty;
        public string Passportnb { get; set; } = string.Empty; 
        public string From { get; set; } = string.Empty;
        public string To { get; set; } = string.Empty;

        public int Status { get; set; }

    }
}
