namespace ZINGMP4.Domain.MISAException
{
    public class ValidateException : Exception
    {
        #region Properties
        public int ErrorCode { get; set; }
        #endregion

        #region Constructors
        public ValidateException() { }

        public ValidateException(int errorCode)
        {
            ErrorCode = errorCode;
        }

        public ValidateException(string message) : base(message) { }

        public ValidateException(int errorCode, string message) : base(message)
        {
            ErrorCode = errorCode;
        }
        #endregion
    }
}
