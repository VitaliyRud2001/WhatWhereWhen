package PackageStructure;

import java.io.Serializable;

public class SheetInfo implements Serializable {
    public String Url;
    public String ID;
    public int CountOfTeams;
    public SheetInfo(String Url)
    {
        this.Url = Url;

    }
    public boolean ParseID()
    {
        try {
            String URL_s = Url.substring(Url.indexOf("/d/") + 3);
            this.ID = URL_s.substring(0, URL_s.indexOf("/"));
        }catch (IndexOutOfBoundsException e)
        {
            return false;

        }
        return true;
    }
}
