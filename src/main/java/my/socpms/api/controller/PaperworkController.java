// src/main/java/my/socpms/api/controller/PaperworkController.java
package my.socpms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import my.socpms.api.dto.ApiResponse;
import my.socpms.api.model.Paperwork;
import my.socpms.api.service.PaperworkService;

@RestController
@RequestMapping("/api/paperworks")
public class PaperworkController {
    
    @Autowired
    private PaperworkService paperworkService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPaperworks() {
        return ResponseEntity.ok(new ApiResponse("success", "Paperworks retrieved", 
            paperworkService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPaperwork(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse("success", "Paperwork retrieved", 
            paperworkService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createPaperwork(@Valid @RequestBody Paperwork paperwork) {
        return ResponseEntity.ok(new ApiResponse("success", "Paperwork created", 
            paperworkService.save(paperwork)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePaperwork(@PathVariable Long id, 
            @Valid @RequestBody Paperwork paperwork) {
        return ResponseEntity.ok(new ApiResponse("success", "Paperwork updated", 
            paperworkService.update(id, paperwork)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePaperwork(@PathVariable Long id) {
        paperworkService.delete(id);
        return ResponseEntity.ok(new ApiResponse("success", "Paperwork deleted"));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ApiResponse> approvePaperwork(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse("success", "Paperwork approved", 
            paperworkService.approve(id)));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<ApiResponse> rejectPaperwork(@PathVariable Long id, 
            @RequestBody String reason) {
        return ResponseEntity.ok(new ApiResponse("success", "Paperwork rejected", 
            paperworkService.reject(id, reason)));
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<ApiResponse> getDepartmentPaperworks(@PathVariable String deptId) {
        return ResponseEntity.ok(new ApiResponse("success", "Department paperworks retrieved", 
            paperworkService.findByDepartment(deptId)));
    }

    @PostMapping("/{id}/hod-approve")
    public ResponseEntity<ApiResponse> approveByHOD(
            @PathVariable Long id,
            @RequestParam String note) {
        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Paperwork approved by HOD",
            paperworkService.approveByHOD(id, note)
        ));
    }

    @PostMapping("/{id}/dean-approve")
    public ResponseEntity<ApiResponse> approveByDean(
            @PathVariable Long id,
            @RequestParam String note) {
        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Paperwork approved by Dean",
            paperworkService.approveByDean(id, note)
        ));
    }
}