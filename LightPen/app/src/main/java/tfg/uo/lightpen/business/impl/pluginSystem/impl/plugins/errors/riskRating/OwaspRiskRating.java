package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.riskRating;

/**
 * Created by Iñigo Llaneza Aller on 25/4/17.
 */

public class OwaspRiskRating {

    /**
     * The next set of factors are related to the VULNERABILITY INVOLVED.
     * The goal here is to estimate the likelihood of the particular vulnerability
     * involved being discovered and exploited. Assume the threat agent selected above.
     */

    /**
     * How easy is it for this group of threat agents to discover this vulnerability?
     * Practically impossible (1), difficult (3), easy (7), automated tools available (9)
     */
    private int easeOfDiscovery;

    /**
     * How easy is it for this group of threat agents to actually exploit this vulnerability?
     * Theoretical (1), difficult (3), easy (5), automated tools available (9)
     */
    private int easeOfExploit;

    /**
     * How well known is this vulnerability to this group of threat agents?
     * Unknown (1), hidden (4), obvious (6), public knowledge (9)
     */
    private int awareness;

    /**
     * How likely is an exploit to be detected? Active detection in application (1),
     * logged and reviewed (3), logged without review (8), not logged (9)
     */
    private int intrusionDetection;

    public OwaspRiskRating(int easeOfDiscovery, int easeOfExploit, int awareness, int intrusionDetection) {
        setEaseOfDiscovery(easeOfDiscovery);
        setEaseOfExploit(easeOfExploit);
        setAwareness(awareness);
        setIntrusionDetection(intrusionDetection);
    }

    public Double getRiskLevel(){
        return 0.0+(getAwareness()+getEaseOfDiscovery()
                +getEaseOfExploit()+getIntrusionDetection())/4;
    }

    //region Getter & Setter
    private int getEaseOfDiscovery() {
        return easeOfDiscovery;
    }

    private void setEaseOfDiscovery(int easeOfDiscovery) {
        this.easeOfDiscovery = easeOfDiscovery;
    }

    private int getEaseOfExploit() {
        return easeOfExploit;
    }

    private void setEaseOfExploit(int easeOfExploit) {
        this.easeOfExploit = easeOfExploit;
    }

    private int getAwareness() {
        return awareness;
    }

    private void setAwareness(int awareness) {
        this.awareness = awareness;
    }

    private int getIntrusionDetection() {
        return intrusionDetection;
    }

    private void setIntrusionDetection(int intrusionDetection) {
        this.intrusionDetection = intrusionDetection;
    }
    //endregion
}
