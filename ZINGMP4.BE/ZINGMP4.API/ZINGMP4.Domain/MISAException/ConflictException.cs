

namespace ZINGMP4.Domain.MISAException
{
    public class ConflictException : Exception
    {
        #region Properties
        public int ErrorCode { get; set; }
        #endregion

        #region Constructors
        public ConflictException() { }

        public ConflictException(int errorCode)
        {
            ErrorCode = errorCode;
        }

        public ConflictException(string message) : base(message) { }

        public ConflictException(int errorCode, string message) : base(message)
        {
            ErrorCode = errorCode;
        }
        #endregion
    }
}
