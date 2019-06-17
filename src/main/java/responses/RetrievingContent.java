package responses;

import operations.ExtendedProperty;
import operations.Info;

import java.util.ArrayList;

public class RetrievingContent {
    private String creationTime;
    private String id;
    private String operation;
    private String organizationId;
    private int recordType;
    private String resultStatus; // values success/failed
    private String userKey;
    private int userType;
    private String workload;
    private String clientIP;
    private String objectId;
    private String userId;
    private int azureActiveDirectoryEventType;
    private ArrayList<ExtendedProperty> extendedProperties;
    private String client;
    private long loginStatus;
    private String userDomain;
    private ArrayList<Info> actor;
    private String actorContextId;
    private String interSystemsId;
    private String intraSystemId;
    private ArrayList<Info> target;
    private String targetContextId;

    public RetrievingContent(String creationTime, String id, String operation, String organizationId,
                             int recordType, String resultStatus, String userKey, int userType,
                             String workload, String clientIP, String objectId, String userId,
                             int azureActiveDirectoryEventType, ArrayList<ExtendedProperty> extendedProperties,
                             String client, long loginStatus, String userDomain, ArrayList<Info> actor,
                             String actorContextId, String interSystemsId, String intraSystemId,
                             ArrayList<Info> target, String targetContextId) {
        this.creationTime = creationTime;
        this.id = id;
        this.operation = operation;
        this.organizationId = organizationId;
        this.recordType = recordType;
        this.resultStatus = resultStatus;
        this.userKey = userKey;
        this.userType = userType;
        this.workload = workload;
        this.clientIP = clientIP;
        this.objectId = objectId;
        this.userId = userId;
        this.azureActiveDirectoryEventType = azureActiveDirectoryEventType;
        this.extendedProperties = extendedProperties;
        this.client = client;
        this.loginStatus = loginStatus;
        this.userDomain = userDomain;
        this.actor = actor;
        this.actorContextId = actorContextId;
        this.interSystemsId = interSystemsId;
        this.intraSystemId = intraSystemId;
        this.target = target;
        this.targetContextId = targetContextId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAzureActiveDirectoryEventType() {
        return azureActiveDirectoryEventType;
    }

    public void setAzureActiveDirectoryEventType(int azureActiveDirectoryEventType) {
        this.azureActiveDirectoryEventType = azureActiveDirectoryEventType;
    }

    public ArrayList<ExtendedProperty> getExtendedProperties() {
        return extendedProperties;
    }

    public void setExtendedProperties(ArrayList<ExtendedProperty> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public long getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(long loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }

    public ArrayList<Info> getActor() {
        return actor;
    }

    public void setActor(ArrayList<Info> actor) {
        this.actor = actor;
    }

    public String getActorContextId() {
        return actorContextId;
    }

    public void setActorContextId(String actorContextId) {
        this.actorContextId = actorContextId;
    }

    public String getInterSystemsId() {
        return interSystemsId;
    }

    public void setInterSystemsId(String interSystemsId) {
        this.interSystemsId = interSystemsId;
    }

    public String getIntraSystemId() {
        return intraSystemId;
    }

    public void setIntraSystemId(String intraSystemId) {
        this.intraSystemId = intraSystemId;
    }

    public ArrayList<Info> getTarget() {
        return target;
    }

    public void setTarget(ArrayList<Info> target) {
        this.target = target;
    }

    public String getTargetContextId() {
        return targetContextId;
    }

    public void setTargetContextId(String targetContextId) {
        this.targetContextId = targetContextId;
    }
}
