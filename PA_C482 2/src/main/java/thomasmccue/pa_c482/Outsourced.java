package thomasmccue.pa_c482;

public class Outsourced extends Part {
    private String companyName;

    /**
     * This is the constructor for an Outsourced Part. It uses the super keyword so that the
     * constructor from the Part class is used, and only the companyName is set differently.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method sets an Outsourced parts companyName to the companyName that is
     * passed as an argument.
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method gets and returns an Outsourced Parts companyName.
     *
     * @return
     */
    public String getCompanyName() {
        return this.companyName;
    }

}
