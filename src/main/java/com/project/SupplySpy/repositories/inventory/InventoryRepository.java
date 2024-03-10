package com.project.SupplySpy.repositories.inventory;

import java.util.List;

import com.project.SupplySpy.classes.Inventory;

public interface InventoryRepository {
    List<Inventory> getInventory(int page, int size);
    void insertInventory(Inventory inventory);
    int getTotalInventoryCount();
    int getNoQuantityInventoryCount();
    void updateInventory(Inventory inventory);
    void deleteInventory(Inventory inventory);
}