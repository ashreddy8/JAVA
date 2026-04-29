import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


interface Product {
   void displayDetails();
}




class NewProduct implements Product {
   private String name;


   public NewProduct(String name) {
       this.name = name;
   }


   @Override
   public void displayDetails() {
       System.out.println("New Product Name: " + name);
   }
}


class LegacyItem {
   private String itemId;
   private String description;


   public LegacyItem(String itemId, String description) {
       this.itemId = itemId;
       this.description = description;
   }


   public void print() {
       System.out.println("Legacy Item [ID: " + itemId + "] - Description: " + description);
   }
}


// --- 4. ProductAdapter Class (Adapter Pattern) ---
class ProductAdapter implements Product {
   private LegacyItem legacyItem;


   public ProductAdapter(LegacyItem legacyItem) {
       this.legacyItem = legacyItem;
   }


   @Override
   public void displayDetails() {
       // Delegates the call to the legacy item's print method
       legacyItem.print();
   }
}


class InventoryManager {
   private static InventoryManager instance;
   private List<Product> inventory;


   private InventoryManager() {
       inventory = new ArrayList<>();
   }


   public static InventoryManager getInstance() {
       if (instance == null) {
           instance = new InventoryManager();
       }
       return instance;
   }


   public void addProduct(Product product) {
       inventory.add(product);
   }


   public Iterator<Product> returnInventory() {
       return inventory.iterator();
   }
}


// --- 6. Main Application ---
public class Main {
   public static void main(String[] args) {
       InventoryManager manager = InventoryManager.getInstance();


       Product laptop = new NewProduct("High-Performance Laptop");
       Product mouse = new NewProduct("Wireless Optical Mouse");
       LegacyItem oldMonitor = new LegacyItem("CRT-001", "15-inch CRT Monitor");
       LegacyItem oldKeyboard = new LegacyItem("KB-099", "PS/2 Mechanical Keyboard");


       Product adaptedMonitor = new ProductAdapter(oldMonitor);
       Product adaptedKeyboard = new ProductAdapter(oldKeyboard);


       manager.addProduct(laptop);
       manager.addProduct(mouse);
       manager.addProduct(adaptedMonitor);
       manager.addProduct(adaptedKeyboard);


       System.out.println("--- Current Inventory Details ---");


       Iterator<Product> inventoryIterator = manager.returnInventory();
      
       while (inventoryIterator.hasNext()) {
           Product currentProduct = inventoryIterator.next();
           currentProduct.displayDetails();
       }
   }
}
