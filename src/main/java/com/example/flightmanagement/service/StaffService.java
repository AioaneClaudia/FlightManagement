package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Staff;
import com.example.flightmanagement.repository.StaffRepository;
import java.util.List;

public class StaffService {
    private StaffRepository staffRepository = new StaffRepository();

    public void addStaff(Staff staff) {
        staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(String id) {
        return staffRepository.findById(id);
    }

    public void removeStaff(String id) {
        staffRepository.delete(id);
    }
}
