package PackageStructure;

import android.widget.Button;

public class packageToSend {
        public Codes code;
        public Codes ResponseCode;
        public SheetInfo sheetInfo;
        public AnswerPackage answer;
        public Button button;
        public String CreateRequest()
        {
            StringBuilder request = new StringBuilder();
            switch(this.code)
            {

                case ANSWER_BTN_CLICKED:
                    request.append("code=191&").append("&range=").append(answer.Range).
                    append("&id=").append(sheetInfo.ID).append("&round=").append(answer.Round).append("&sheet_name=Sheet1");
                    break;

                case GET_TEAMS_COUNT:
                    request.append("code=").append(192).append("&id=").append(sheetInfo.ID);
                    break;

                default:
                    break;
            }
            return request.toString();
        }

}
