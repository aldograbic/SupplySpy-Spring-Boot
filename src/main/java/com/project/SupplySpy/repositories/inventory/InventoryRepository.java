package com.project.SupplySpy.repositories.inventory;

import java.util.List;

import com.project.SupplySpy.classes.Inventory;

public interface InventoryRepository {
    List<Inventory> getInventoryForUserByUserId(int userId);
    void insertInventory(Inventory inventory);
}