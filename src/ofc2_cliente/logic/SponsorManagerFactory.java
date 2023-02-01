/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ofc2_cliente.logic;

/**
 * Factory
 * @author Elias
 */
public class SponsorManagerFactory {
    public SponsorManager createSponsorManager(){
        SponsorManager sponsorManager = new SponsorRESTfulClient();
        return sponsorManager;
    }
}
