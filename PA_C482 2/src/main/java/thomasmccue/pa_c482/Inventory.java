package thomasmccue.pa_c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.IOException;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Uses the .add() function of ObservableList to add newPart to allParts list.
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Uses the .add() function of ObservableList to add newProduct to allProducts list.
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Uses a partId passed as an argument to find that specific part in the allParts ObservableList.
     *
     * @param partId
     * @return the found part, if a part with a matching ID is found.
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Uses a productId passed as an argument to find that specific product in the allProducts ObservableList.
     *
     * @param productId
     * @return the found product, if a product with a matching ID is found.
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Searches the allParts list for any parts that contain the argument String partName.
     *
     * @param partName
     * @return a new ObservableList of Parts called searchResults
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                searchResults.add(part);
            }
        }
        if (searchResults.isEmpty()) {
            return null;
        } else {
            return searchResults;
        }
    }

    /**
     * Searches the allProducts list for any products that contain the argument String productName.
     *
     * @param productName
     * @return a new ObservableList of Products called searchResults
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                searchResults.add(product);
            }
        }
        if (searchResults.isEmpty()) {
            return null;
        } else {
            return searchResults;
        }
    }

    /**
     * Uses .set() method of ObservableList to replace the part that was at index with the updated version
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Uses .set() method of ObservableList to replace the product that was at index with the updated version
     *
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Uses the .remove() method of ObservableList to delete the selectedPart that's been passed as an argument.
     *
     * @param selectedPart
     * @return true if selectedPart is deleted successfully, else return false
     */
    public static boolean deletePart(Part selectedPart) {
        boolean deleted = allParts.remove(selectedPart);
        if (deleted) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Holds any associated parts for selectedProduct in an ObservableList, if there are no associated
     * parts for the selected product, attempt to delete. "The user should not delete a product that has
     * a part associated with it."
     *
     * @param selectedProduct
     * @return true if selectedProduct is deleted successfully, else return false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
        if (associatedParts.isEmpty()) {
            boolean deleted = allProducts.remove(selectedProduct);
            if (deleted) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an ObservableList of all parts in inventory.
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns an ObservableList of all products in inventory.
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
