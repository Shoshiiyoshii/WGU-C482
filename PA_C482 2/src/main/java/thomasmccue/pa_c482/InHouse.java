package thomasmccue.pa_c482;

public class InHouse extends Part {
    private int machineId;

    /**
     * This is the constructor for an InHouse Part. It uses the super keyword so that the
     * constructor from the Part class is used, and only the machineId is set differently.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * This method sets an InHouse parts machineId to the machineId that is
     * passed as an argument.
     *
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * This method gets and returns an InHouse Parts machineId.
     *
     * @return machineId
     */
    public int getMachineId() {
        return this.machineId;
    }
}
