namespace ZINGMP4.Domain.MISAException
{
    public class NotFoundException:Exception
    {

        #region Properties
        public int ErrorCode { get; set; } 
        #endregion

        #region Constructors
        public NotFoundException() { }

        public NotFoundException(int errorCode)
        {
            ErrorCode = errorCode;
        }

        public NotFoundException(string message) : base(message) { }

        public NotFoundException(string message, int errorCode) : base(message)
        {
            ErrorCode = errorCode;
        } 
        #endregion
    }
}
