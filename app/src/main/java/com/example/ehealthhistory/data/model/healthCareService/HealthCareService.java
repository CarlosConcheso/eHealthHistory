package com.example.ehealthhistory.data.model.healthCareService;

import com.example.ehealthhistory.data.model.footballer.Footballer;

/**
 * o	Alcance y Uso:
 *      	Se utiliza para describir un solo servicio de atención médica o
 *          una categoría de servicios que proporciona una organización en una ubicación.
 *      	Servicio de Podología, Servicios de emergencia del hospital de Oviedo,
 *          Cuidado de relevo proporcionado en un asilo de ancianos o en un albergue,
 *          Servicio de asesoramiento telefónico de crisis 24 horas...
 */
public class HealthCareService {

    private String username;
    private boolean active;
    private String category;
    private String name;
    private String extraDetails;
    private HealthCareAvalibleTime avalibleTime;

    public HealthCareService(){}

    public HealthCareService(String username,boolean active, String name, String category, String extraDetails,
                             HealthCareAvalibleTime avalibleTime) {
        this.username = username;
        this.active = active;
        this.name = name;
        this.category = category;
        this.extraDetails = extraDetails;
        this.avalibleTime=avalibleTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public HealthCareAvalibleTime getAvalibleTime()
    {
        return avalibleTime;
    }

    public void setAvalibleTime(HealthCareAvalibleTime avalibleTime) {
        this.avalibleTime = avalibleTime;
    }
}
