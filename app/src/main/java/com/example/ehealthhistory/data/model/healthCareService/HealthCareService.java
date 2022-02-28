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

    int id;
    private boolean active;
    private Footballer footballer;
    private String category;
    private String name;
    private String extraDetails;
    private HealthCareAvalibleTime avalibleTime;

    public HealthCareService(){}

    public HealthCareService(boolean active, Footballer footballer, String category, String name, String extraDetails,
                             HealthCareAvalibleTime avalibleTime) {
        this.active = active;
        this.footballer = footballer;
        this.category = category;
        this.name = name;
        this.extraDetails = extraDetails;
        this.avalibleTime=avalibleTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setClub(Footballer footballer) {
        this.footballer = footballer;
    }

    public HealthCareAvalibleTime getAvalibleTime()
    {
        return avalibleTime;
    }

}
