package com.newgen.iforms.user;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.utils.Constants;
import com.newgen.utils.LogGen;
import com.newgen.worksteps.TreasuryOfficerMaker;
import com.newgen.worksteps.TreasuryOfficerInitiator;
import com.newgen.worksteps.TreasuryOfficerVerifier;
import com.newgen.worksteps.TreasuryOpsVerifier;
import com.newgen.worksteps.TreasuryOpsMature;
import com.newgen.worksteps.AwaitingMaturity;
import com.newgen.worksteps.TreasuryOpsMatureOnMaturity;
import com.newgen.worksteps.BranchInitiator;
import com.newgen.worksteps.BranchVerifier;
import com.newgen.worksteps.BranchException;
import com.newgen.worksteps.RpcVerifier;
import com.newgen.worksteps.TreasuryOpsFailed;
import com.newgen.worksteps.TreasuryOpsSuccessful;
import com.newgen.worksteps.Exit;
import org.apache.log4j.Logger;

public class MoneyMarketW implements IFormListenerFactory, Constants {
private Logger logger = LogGen.getLoggerInstance(MoneyMarketW.class);
	@Override
	public IFormServerEventHandler getClassInstance(IFormReference ifr) {
		// TODO Auto-generated method stub
		IFormServerEventHandler objActivity  = null;
		String processName = ifr.getProcessName();
		logger.info("processName: "+processName);
		String activityName =ifr.getActivityName();
		logger.info("activityName: "+activityName);
		try {
		if (processName.equalsIgnoreCase(ProcessName)){
			if (activityName != null && activityName.trim().equalsIgnoreCase(treasuryOfficerInitiator))
				objActivity = new TreasuryOfficerInitiator();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(treasuryOfficerMaker))
				objActivity = new TreasuryOfficerMaker();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(treasuryOfficerVerifier))
				objActivity = new TreasuryOfficerVerifier();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(treasuryOpsVerifier))
				objActivity = new TreasuryOpsVerifier();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(treasuryOpsMature))
				objActivity = new TreasuryOpsMature();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(awaitingMaturityUtility))
				objActivity = new AwaitingMaturity();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(treasuryOpsMatureOnMaturity))
				objActivity = new TreasuryOpsMatureOnMaturity();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(branchInitiator))
				objActivity = new BranchInitiator();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(branchVerifier))
				objActivity = new BranchVerifier();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(branchException))
				objActivity = new BranchException();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(rpcVerifier))
				objActivity = new RpcVerifier();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(treasuryOpsFailed))
				objActivity = new TreasuryOpsFailed();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(awaitingMaturity))
				objActivity = new AwaitingMaturity();

			else if (activityName != null && activityName.trim().equalsIgnoreCase(treasuryOpsSuccessful))
				objActivity = new TreasuryOpsSuccessful();

			else if (activityName != null && (activityName.trim().equalsIgnoreCase(discardWs) || activityName.trim().equalsIgnoreCase(query) || activityName.trim().equalsIgnoreCase(exit)))
				objActivity = new Exit();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return objActivity;
	}

}
