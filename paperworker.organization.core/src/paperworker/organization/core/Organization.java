package paperworker.organization.core;

import paperworker.core.PWError;
import paperworker.core.PWItem;
import paperworker.core.annotation.PWFieldBasicInfo;
import paperworker.core.annotation.PWItemBasicInfo;
import paperworker.master.core.MasterItem;

@PWItemBasicInfo(caption = "Organizations", tableName = "Organizations")
public class Organization extends MasterItem {

	@PWFieldBasicInfo(caption = "Organization ID", type = "varchar(100)", primary = true)
	private String organizationId;

	@PWFieldBasicInfo(caption = "Parent Organization ID", type = "varchar(100)")
	private String parentOrganizationId;
	
	@PWFieldBasicInfo(caption = "Group Name", type = "varchar(100)")
	private String name;
	
	@PWFieldBasicInfo(caption = "Group Short Name", type = "varchar(100)")
	private String shortName;
	
	@PWFieldBasicInfo(caption = "Group Leader ID", type = "varchar(100)")
	private String leaderId;
	
	@PWFieldBasicInfo(caption = "Group Subleader ID", type = "varchar(100)")
	private String subleaderId;
	
	@PWFieldBasicInfo(caption = "Division", type = "varchar(100)")
	private OrganizationDivision division;
	
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getSubleaderId() {
		return subleaderId;
	}

	public void setSubleaderId(String subleaderId) {
		this.subleaderId = subleaderId;
	}

	public OrganizationDivision getDivision() {
		return division;
	}

	public void setDivision(OrganizationDivision division) {
		this.division = division;
	}
	
	@Override
	public String getId() {
		return organizationId;
	}

	@Override
	public void setId(String itemId) {
		organizationId = itemId;
	}

	@Override
	public Object getValue(String fieldName) throws PWError {
		return PWItem.getValue(this, fieldName);
	}

	@Override
	public void setValue(String fieldName, Object value) throws PWError {
		PWItem.setValue(this, fieldName, value);
	}

}
