package com.newgen.worksteps;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.utils.Commons;
import com.newgen.utils.CommonsI;
import com.newgen.utils.LogGen;
import com.newgen.utils.MailSetup;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TreasuryOfficerInitiator extends Commons implements IFormServerEventHandler, CommonsI {
    private Logger logger = LogGen.getLoggerInstance(TreasuryOfficerInitiator.class);
    @Override
    public void beforeFormLoad(FormDef formDef, IFormReference ifr) {
        try {
        	beforeFormLoadActivity(ifr);
        }
        catch (Exception e){ logger.error("Exception-- "+ e.getMessage());}
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
    public String executeServerEvent(IFormReference ifr, String control, String event, String data) {
        try {
            switch (event){
                case formLoad:{}
                break;
                case onLoad:{}
                break;
                case onClick:{
                    switch (control){
                        case goToDashBoard:{
                            backToDashboard(ifr);
                            if (getProcess(ifr).equalsIgnoreCase(commercialProcess))
                                cpBackToDashboard(ifr);
                           else if (getProcess(ifr).equalsIgnoreCase(treasuryProcess))
                                tbBackToDashboard(ifr);
                            clearFields(ifr,new String[] {selectProcessLocal});
                            break;
                        }
                    }
                }
                break;
                case onChange:{
                    switch (control){
                        case onChangeProcess: {
                            selectProcessSheet(ifr);
                            if (getProcess(ifr).equalsIgnoreCase(commercialProcess)) cpFormLoadActivity(ifr);
                            else if (getProcess(ifr).equalsIgnoreCase(treasuryProcess)) 
                            	tbInitiatorFormLoad(ifr);
                            break;
                        }
                    }
                }
                break;
                case custom:{}
                break;
                case onDone:{}
                break;
                case decisionHistory:{
                   if (getProcess(ifr).equalsIgnoreCase(commercialProcess))
                       setCpDecisionHistory(ifr);
                   else if (getProcess(ifr).equalsIgnoreCase(treasuryProcess)) 
                	   setTbDecisionHistory(ifr);
                }
                break;
                case sendMail:{
                    if (getProcess(ifr).equalsIgnoreCase(commercialProcess))
                        cpSendMail(ifr);
                    else if (getProcess(ifr).equalsIgnoreCase(treasuryProcess)) 
                    	tbSendMail(ifr);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.info("Exception Occurred-- "+ e.getMessage());
        }
        return null;
    }


    @Override
    public void cpSendMail(IFormReference ifr){
        String message = "A window open request for Commercial Paper has been Initiated with ref number "+getWorkItemNumber(ifr)+".";
        new MailSetup(ifr,getWorkItemNumber(ifr),getUsersMailsInGroup(ifr,groupName),empty,mailSubject,message);
    }
    @Override
    public void cpFormLoadActivity(IFormReference ifr){
        cpSetDecision(ifr);
        setVisible(ifr, new String[]{cpLandingMsgSection, cpDecisionSection, cpMarketSection});
        enableFields(ifr,new String[]{cpLandMsgLocal,cpSelectMarketLocal});
        setMandatory(ifr,new String [] {cpSelectMarketLocal,cpLandMsgLocal,cpDecisionLocal,cpRemarksLocal});
    }
    public void beforeFormLoadActivity(IFormReference ifr){
        hideProcess(ifr);
        hideCpSections(ifr);
        hideTbSections(ifr);
        hideShowLandingMessageLabel(ifr,False);
        hideShowBackToDashboard(ifr,False);
        setGenDetails(ifr);
        tbSetDecision(ifr);
        clearFields(ifr,new String [] {selectProcessLocal});
        setMandatory(ifr, new String[]{selectProcessLocal});
        setFields(ifr, new String[]{currWsLocal,prevWsLocal},new String[]{getCurrentWorkStep(ifr),na});
        setVisible(ifr, new String[]{tbLandingMsgSection, tbDecisionSection, tbMarketSection});
    }

    @Override
    public void cpSetDecision(IFormReference ifr) {
        setDecision(ifr,cpDecisionLocal,new String[]{decSubmit,decDiscard});
    }


    private void cpBackToDashboard(IFormReference ifr) {
        undoMandatory(ifr,new String [] {cpSelectMarketLocal,cpLandMsgLocal,cpDecisionLocal,cpRemarksLocal});
        clearFields(ifr,new String [] {cpSelectMarketLocal,cpLandMsgLocal,cpDecisionLocal,cpRemarksLocal});
    }

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
    
    /******************  TREASURY BILL CODE BEGINS *********************************/
    
    private void tbBackToDashboard(IFormReference ifr) {
        undoMandatory(ifr,new String [] {tbSelectMarketLocal,tbLandMsgLocal,tbDecisionLocal});
        clearFields(ifr,new String [] {tbSelectMarketLocal,tbLandMsgLocal,tbDecisionLocal});
    }
    public void tbFormLoadActivity(IFormReference ifr){
        hideProcess(ifr);
        hideTbSections(ifr);
        hideShowLandingMessageLabel(ifr,False);
        hideShowBackToDashboard(ifr,False);
        setGenDetails(ifr);
        tbSetDecision(ifr);
        clearFields(ifr,selectProcessLocal);
        setMandatory(ifr, selectProcessLocal);
        ifr.setValue(currWsLocal, getActivityName(ifr));
        ifr.setValue(prevWsLocal, na);
        setVisible(ifr, new String[]{tbLandingMsgSection, tbDecisionSection, tbMarketSection});
    }
    public void tbSetDecision(IFormReference ifr) {
        ifr.addItemInCombo(tbDecisionLocal,decSubmit,decSubmit);
        ifr.addItemInCombo(tbDecisionLocal,decDiscard,decDiscard);
    }
    private void tbInitiatorFormLoad (IFormReference ifr){
    	setMandatory(ifr,new String [] {tbSelectMarketLocal,tbLandMsgLocal,tbDecisionLocal});
    }
    public void tbSendMail(IFormReference ifr){
        String message = "A window open request for "+treasuryProcessName+" has been Initiated with ref number "+getWorkItemNumber(ifr)+".";
        new MailSetup(ifr,getWorkItemNumber(ifr),getUsersMailsInGroup(ifr,groupName),empty,mailSubject,message);
    }
    
    /******************  TREASURY BILL CODE ENDS ***********************************/
}