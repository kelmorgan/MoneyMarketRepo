package com.newgen.worksteps;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.reusableObject.Commons;
import com.newgen.reusableObject.CommonsI;
import com.newgen.utils.LogGen;
import com.newgen.utils.MailSetup;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TreasuryOfficerVerifier extends Commons implements IFormServerEventHandler, CommonsI {
    private static Logger logger = LogGen.getLoggerInstance(TreasuryOfficerVerifier.class);
    @Override
    public void beforeFormLoad(FormDef formDef, IFormReference ifr) {
        clearDecHisFlag(ifr);
        if (!isEmpty(getProcess(ifr))) showSelectedProcessSheet(ifr);
        if (getProcess(ifr).equalsIgnoreCase(commercialProcess)) cpFormLoadActivity(ifr);
        else if (getProcess(ifr).equalsIgnoreCase(treasuryProcess))
        	tbFormLoadActivity(ifr);
    }

    @Override
    public String setMaskedValue(String s, String s1) {
        return s1;
    }

    @Override
    public JSONArray executeEvent(FormDef formDef, IFormReference iFormReference, String s, String s1) {
        return null;
    }

    @Override
    public String executeServerEvent(IFormReference ifr, String controlName, String eventName, String data) {
        try {
            switch (eventName){
                case formLoad:{}
                break;
                case onLoad:{}
                break;
                case onClick:{}
                break;
                case onChange:{}
                break;
                case custom:{}
                break;
                case onDone:{}
                break;
                case decisionHistory:{if (getProcess(ifr).equalsIgnoreCase(commercialProcess)) setCpDecisionHistory(ifr); }
                break;
                case sendMail:{ if (getProcess(ifr).equalsIgnoreCase(commercialProcess)) cpSendMail(ifr);}
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.info("Exception Occurred-- "+ e.getMessage());
        }
        return null;
    }

    @Override
    public void cpSendMail(IFormReference ifr) {
        String message;
        if (getPrevWs(ifr).equalsIgnoreCase(treasuryOfficerInitiator)){
            if (getCpDecision(ifr).equalsIgnoreCase(decApprove)) {
                message = "Landing Message has been approved by the treasury officer verifier with ref No. "+getWorkItemNumber(ifr)+". Login to setup market.";
                new MailSetup(ifr, getWorkItemNumber(ifr), getUsersMailsInGroup(ifr, groupName), empty, mailSubject, message);
            }
            else {
                message = "Landing Message has been rejected by the treasury officer verifier with ref No. "+getWorkItemNumber(ifr)+". Login to make necessary corrections.";
                new MailSetup(ifr, getWorkItemNumber(ifr), getUsersMailsInGroup(ifr, groupName), empty, mailSubject, message);
            }
    }
    }
    @Override
    public void cpFormLoadActivity(IFormReference ifr){
        hideCpSections(ifr);
        hideShowLandingMessageLabel(ifr,False);
        setGenDetails(ifr);
        disableCpSections(ifr);
        hideShowBackToDashboard(ifr,False);
        if (getPrevWs(ifr).equalsIgnoreCase(treasuryOfficerInitiator)) {
            setVisible(ifr,new String[] {cpLandingMsgSection,cpDecisionSection,cpMarketSection});
            enableFields(ifr,new String[]{cpDecisionSection});
            setMandatory(ifr, new String[]{cpDecisionLocal,cpRemarksLocal});
        }
        cpSetDecision(ifr);
    }

    @Override
    public void cpSetDecision(IFormReference ifr) {
        ifr.clearCombo(cpDecisionLocal);
        clearFields(ifr,new String[]{cpDecisionLocal,cpRemarksLocal});
        ifr.addItemInCombo(cpDecisionLocal, decApprove, decApprove);
        ifr.addItemInCombo(cpDecisionLocal, decReject, decReject);
    }
    
    //*************** Treasury Start *************************/
    
    private void tbFormLoadActivity(IFormReference ifr){
        hideTbSections(ifr);
        hideShowLandingMessageLabel(ifr,False);
        setGenDetails(ifr);
        disableCpSections(ifr);
        hideShowBackToDashboard(ifr,False);
        //set controls for task to be performed
        //approving of landing message 
        if (getPrevWs(ifr).equalsIgnoreCase(treasuryOfficerInitiator) || getTbUpdateLocal(ifr)) { // for approval of landing page
            setVisible(ifr,new String[] {tbLandingMsgSection,tbDecisionSection,tbMarketSection});
            enableField(ifr,tbDecisionSection);
            setMandatory(ifr, new String[]{tbDecisionLocal,tbRemarksLocal});
        }
        //Modification of Primary Market Cut-off Time 
       // else if()
       // tbSetDecision(ifr);
    }

    private void tbSetDecision(IFormReference ifr) {
        ifr.clearCombo(tbDecisionLocal);
        clearFields(ifr,new String[]{tbDecisionLocal,tbRemarksLocal});
        ifr.addItemInCombo(tbDecisionLocal, decApprove, decApprove);
        ifr.addItemInCombo(tbDecisionLocal, decReject, decReject);
    }
    
    //*************** Treasury End *************************/

    @Override
    public JSONArray validateSubmittedForm(FormDef formDef, IFormReference iFormReference, String s) {
        return null;
    }

    @Override
    public String executeCustomService(FormDef formDef, IFormReference iFormReference, String s, String s1, String s2) {
        return null;
    }

    @Override
    public String getCustomFilterXML(FormDef formDef, IFormReference iFormReference, String s) {
        return null;
    }

    @Override
    public String generateHTML(EControl eControl) {
        return null;
    }

    @Override
    public String introduceWorkItemInWorkFlow(IFormReference iFormReference, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }
}
