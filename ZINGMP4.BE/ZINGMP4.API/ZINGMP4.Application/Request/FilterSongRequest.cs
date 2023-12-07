using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ZINGMP4.Application.Request
{
    public class FilterSongRequest
    {
        public int take { get; set; } = 20;
        public int skip { get; set; } = 1;

        public string filter { get; set; } = string.Empty;
    }
}
