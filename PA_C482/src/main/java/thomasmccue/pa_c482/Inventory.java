package thomasmccue.pa_c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    ;
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    ;

    //uses the .add() function of ObservableList to add newPart to allParts
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    //uses the .add() function of ObservableList to add newProduct to allProducts
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    //uses a partId passed as an argument to find that specific part in the allParts ObservableList
    public static Part lookupPart(int partId) {
        //iterate through every Part object in the allParts ObservableList until the part id matches
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        //returns null if no matching part is found.
        return null;
    }

    //uses a productId passed as an argument to find that specific product in the allProducts ObservableList
    //works the same as lookupPart(int) method
    public static Product lookupProduct(int productId) {
        //iterate through every Product object in the allProduct ObservableList until the product id matches
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        //returns null if no matching product is found.
        return null;
    }

    //searches the allParts list for any parts that contain the argument String partName,
    //and returns a new ObservableList of Parts called searchResults
    public static ObservableList<Part> lookupPart(String partName) {
        //create a local ObservableList of Parts called search results to add results to so that multiple matches can be returned
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        //for each part in the allParts list, check to see if the name of the part matches the partName String
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                //then add any matching parts to the searchResults list
                searchResults.add(part);
            }
        }
        //if there is anything in the search results list, return the list, otherwise return null
        if (searchResults.isEmpty()) {
            return null;
        } else {
            return searchResults;
        }
    }

    //searches the allProducts list for any products that contain the argument String productName,
    //and returns a new ObservableList of Products called searchResults.
    //Essentially the same as lookupPart(String partName) method.
    public static ObservableList<Product> lookupProduct(String productName) {
        //create a local ObservableList of Products called search results to add results to so that multiple matches can be returned
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        //for each product in the allProducts list, check to see if the name of the product matches the productName String
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                //then add any matching products to the searchResults list
                searchResults.add(product);
            }
        }
        //if there is anything in the search results list, return the list, otherwise return null
        if (searchResults.isEmpty()) {
            return null;
        } else {
            return searchResults;
        }
    }

    public static void updatePart(int index, Part selectedPart) {
        //use .set() method of ObservableList to replace the part that was at index with the updated version
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        //use .set() method of ObservableList to replace the product that was at index with the updated version
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        boolean deleted = allParts.remove(selectedPart);
        if (deleted) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteProduct(Product selectedProduct) {
        //Hold any associated parts for selectedProduct in an ObservableList
        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
        //If there are no associated parts for the selected product, attempt to delete. "The user should not delete a product that has a part associated with it."
        if (associatedParts.isEmpty()) {
            boolean deleted = allProducts.remove(selectedProduct);
            //If delete was successful, return true, else return false.
            if (deleted) {
                return true;
                //associatedParts is not empty, selected product should not be deleted.
            }
        }
        return false;
    }


    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
