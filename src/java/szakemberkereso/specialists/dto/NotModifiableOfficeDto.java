/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szakemberkereso.specialists.dto;

import java.util.List;

/**
 *
 * @author gusztafszon
 */
public class NotModifiableOfficeDto {
    private Long id;

    private String address;
    
    private String userGivenAddress;
    
    private Float lat;
    
    private Float lng;
    
    private List<OpeningHoursDto> openingHours;
    
    private Boolean denied;
    
    private Boolean verified;
    
    private String deniedMessage;
    
    private Boolean modified;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserGivenAddress() {
        return userGivenAddress;
    }

    public void setUserGivenAddress(String userGivenAddress) {
        this.userGivenAddress = userGivenAddress;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public List<OpeningHoursDto> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHoursDto> openingHours) {
        this.openingHours = openingHours;
    }

    public Boolean getDenied() {
        return denied;
    }

    public void setDenied(Boolean denied) {
        this.denied = denied;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getDeniedMessage() {
        return deniedMessage;
    }

    public void setDeniedMessage(String deniedMessage) {
        this.deniedMessage = deniedMessage;
    }

    //helping jackson tof ind modified shit. should user something smarter solution (Baseclass and abstract mehods maybe)
    public void setModifiedOpeningHours(List<OpeningHoursDto> openingHours) {
        this.openingHours = openingHours;
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }
}
