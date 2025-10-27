package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Staff;
import java.util.*;

public class StaffRepository {
    private Map<String, Staff> staffMembers = new HashMap<>();

    public void save(Staff staff) {
        staffMembers.put(staff.getId(), staff);
    }

    public List<Staff> findAll() {
        return new ArrayList<>(staffMembers.values());
    }

    public Staff findById(String id) {
        return staffMembers.get(id);
    }

    public void delete(String id) {
        staffMembers.remove(id);
    }
}
