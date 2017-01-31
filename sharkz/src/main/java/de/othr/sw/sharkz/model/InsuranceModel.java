package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.BankingData;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceRef;
import othr.de.sw.project.service.*;

@ViewScoped
@Named("insurance")
public class InsuranceModel implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/MarcelKirchner/LivingInsuranceService.wsdl")
    private LivingInsuranceServiceService service;
    
    // Attributes
    private Date startDate;
    private Date endDate;
    private HouseType houseType;
    private HeatingType heatingType;
    private String area;
    private String rooms;
    private String stages;
    private String year;
    private boolean garage;
    private String pets;
    
    // Banking & Account data
    private String bic;
    private String iban;
    private String bankingPassword;
    private String email;
    private String password;
    
    // Result
    private LivingInsuranceTransaction result;
    private long cost;
    private UIComponent button;
    int step = 0;
    
    // Ajax
    private String buttonText = "BERECHNEN";
    private boolean isCalculated = false;
    
    // Models & Services
    @Inject AccountModel accountModel;
    @Inject AccountService accountService;
    
    @PostConstruct
    public void init() {
        if (accountModel.getUser() == null)
            return;

        Customer c = (Customer) accountModel.getUser();
        c = accountService.findCustomer(c.getID());
        
        BankingData b = c.getBankingData();
        
        if (b != null) {
            iban = c.getBankingData().getIban();
            bic = c.getBankingData().getBic();
        }
    }
    
    public String next() {
        if (step == 2) {
            step = 0;
            isCalculated = false;
        }
        
        if (step == 0) {
        
            buttonText = "VERSICHERN";
            calculate();
            
            if (accountModel.isIsLoggedIn()) {
                Customer c = accountService.findCustomer(accountModel.getUser()
                        .getID());

                this.iban = c.getBankingData().getIban();
                this.bic = c.getBankingData().getBic();
            }
        }
        
        else if (step == 1) {
            buy();
        }
        
        return null;
    }
    
    public void calculate() {
        
        try { // Call Web Service Operation
            LivingInsuranceService port = service.getLivingInsuranceServicePort();
            LivingInsuranceTransaction trans = new LivingInsuranceTransaction();
            
            DatatypeFactory fac = DatatypeFactory.newInstance();
            
            GregorianCalendar start = new GregorianCalendar();
            GregorianCalendar end = new GregorianCalendar();
            
            start.setTime(startDate);
            end.setTime(endDate);
            
            trans.setAnimals(Integer.parseInt(pets));
            trans.setEndDate(fac.newXMLGregorianCalendar(end));
            trans.setGarage(garage);
            trans.setHeatingType(heatingType);
            trans.setHouseType(houseType);
            trans.setLivingSpace(Long.parseLong(area));
            trans.setRooms(Integer.parseInt(rooms));
            trans.setStages(Integer.parseInt(stages));
            trans.setStartDate(fac.newXMLGregorianCalendar(start));
            trans.setYearOfBuild(Integer.parseInt(year));
            
            result = port.calculateLivingInsurance(trans);
        } catch (Exception ex) {
            return;
        }
        
        checkResult(false);
    }

    public void buy() {
        
        try { // Call Web Service Operation
            LivingInsuranceService port = service.getLivingInsuranceServicePort();
            
            result.setAccountPassword(password);
            result.setBankPassword(bankingPassword);
            result.setEmailAddress(email);
            result.setIBAN(iban);
            result.setBIC(bic);
            
            result = port.buyLivingInsurance(result);
            
        } catch (Exception ex) {
            return;
        }
        
        checkResult(true);
        
        return;

    }
    
    private void checkResult(boolean isSale) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        switch (result.getInfoMsg().charAt(0)) {
            case '0':
                this.cost = result.getCost();
                step++;
                isCalculated = true;
                if (isSale)
                    ctx.addMessage(button.getClientId(), new FacesMessage(
                            "Sie haben die Versicherung für " + this.cost
                                    + "€ gekauft!"));
                break;
            case '1':
                ctx.addMessage(button.getClientId(), new FacesMessage(
                        "Bitte geben Sie eine valide E-Mail Adresse ein!"));
                break;
            case '2':
                ctx.addMessage(button.getClientId(), new FacesMessage(
                        "Ihr Passwort ist unsicher oder Ihre E-Mail Adresse "
                                + "ist bereits bei MK Versicherungen "
                                + "registriert!"));
                break;
            case '3':
                ctx.addMessage(button.getClientId(), new FacesMessage(
                        "Ungültige Werte: Bitte überprüfen Sie Ihre Angaben!"));
                break;
            case '4':
                ctx.addMessage(button.getClientId(), new FacesMessage(
                        "Sie haben bereits eine Hausratsversicherung "
                                + "abgeschlossen!"));
                break;
            case '5':
                ctx.addMessage(button.getClientId(), new FacesMessage(
                        "Es sidn interne Fehler aufgetreten!"));
                break;
        }
    }
    
    public String getLabel(HouseType t) {
        switch (t) {
            case VILLA:
                return "Villa";
            case SINGLE_FAMILY:
                return "Einfamilienhaus";
            case SEMI_DETACHED:
                return "Doppelhaushälfte";
            case ROW_HOUSE:
                return "Reihenhaus";
            case APARTMENT:
                return "Appartment";
            case GROUND_FLOOR:
                return "Erdgeschosswohnung";
            case ATTIC:
                return "Dachgeschosswohnung";
            case PENTHOUSE:
                return "Penthouse";
            case BUREAU:
                return "Büro";
            case WAREHOUSE:
                return "Lagerhalle";
            case SURGERY:
                return "Praxis";
        }
        
        return "";
    }
    
    public String getLabel(HeatingType t) {
        switch (t) {
            case SOLAR:
                return "Solar";
            case WIND:
                return "Wind";
            case WOOD:
                return "Holz";
            case PELLET:
                return "Pellet";
            case ELECTRICITY:
                return "Elektrisch";
            case OIL:
                return "Öl";
            case GAS:
                return "Gas";
        }
        
        return "";
    }
    
    public HouseType[] getHouseTypeValues() {
        return HouseType.values();
    }
    
    public HeatingType[] getHeatingTypeValues() {
        return HeatingType.values();
    }
    
    // Getter & Setter
    public LivingInsuranceServiceService getService() {
        return service;
    }

    public void setService(LivingInsuranceServiceService service) {
        this.service = service;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public HeatingType getHeatingType() {
        return heatingType;
    }

    public void setHeatingType(HeatingType heatingType) {
        this.heatingType = heatingType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public boolean isIsCalculated() {
        return isCalculated;
    }

    public void setIsCalculated(boolean isCalculated) {
        this.isCalculated = isCalculated;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBankingPassword() {
        return bankingPassword;
    }

    public void setBankingPassword(String bankingPassword) {
        this.bankingPassword = bankingPassword;
    }

    public UIComponent getButton() {
        return button;
    }

    public void setButton(UIComponent button) {
        this.button = button;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
