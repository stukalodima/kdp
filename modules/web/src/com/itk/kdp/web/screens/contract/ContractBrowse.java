package com.itk.kdp.web.screens.contract;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Contract;

@UiController("kdp_Contract.browse")
@UiDescriptor("contract-browse.xml")
@LookupComponent("contractsTable")
@LoadDataBeforeShow
public class ContractBrowse extends StandardLookup<Contract> {
}