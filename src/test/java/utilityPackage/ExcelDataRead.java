package utilityPackage;

public class ExcelDataRead {
    //Initial Excel file data reader;
    ExcelDataConfig excel = new ExcelDataConfig( "./TestData/TestData.xlsx" );

    //Read data from excel file --EditProfile
    public String vailabilityType = excel.getData( "EditProfile", 1, 0 ).trim();
    public String availableHours = excel.getData( "EditProfile", 2, 0 ).trim();
    public String earnTarget = excel.getData( "EditProfile", 3, 0 ).trim();
    public String addLanguage = excel.getData( "EditProfile", 1, 1 ).trim();
    public String languageLevel = excel.getData( "EditProfile", 2, 1 ).trim();
    //Read date from excel file --Delete
    public String title = excel.getData( "ActionOnTable", 1, 0 ).trim();
    public String specifiedContent = excel.getData( "ActionOnTable", 1, 1 ).trim();
    public String action = excel.getData( "ActionOnTable", 1, 2 ).trim();
    public String subUrl = excel.getData( "ActionOnTable", 1, 3 ).trim();
    public int statuCode = Integer.parseInt( excel.getData( "ActionOnTable", 1, 4 ).trim() );

}
