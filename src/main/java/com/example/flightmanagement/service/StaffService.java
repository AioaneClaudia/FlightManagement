package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Staff;
import com.example.flightmanagement.repository.StaffRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(String id) {
        Optional<Staff> opt = staffRepository.findById(id);
        return opt.orElse(null);
    }

    public void removeStaff(String id) {
        staffRepository.deleteById(id);
    }
}
