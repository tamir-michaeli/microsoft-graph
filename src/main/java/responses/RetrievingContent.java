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

    private void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    private void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    private void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public int getRecordType() {
        return recordType;
    }

    private void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    private void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getUserKey() {
        return userKey;
    }

    private void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public int getUserType() {
        return userType;
    }

    private void setUserType(int userType) {
        this.userType = userType;
    }

    public String getWorkload() {
        return workload;
    }

    private void setWorkload(String workload) {
        this.workload = workload;
    }

    public String getClientIP() {
        return clientIP;
    }

    private void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getObjectId() {
        return objectId;
    }

    private void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAzureActiveDirectoryEventType() {
        return azureActiveDirectoryEventType;
    }

    private void setAzureActiveDirectoryEventType(int azureActiveDirectoryEventType) {
        this.azureActiveDirectoryEventType = azureActiveDirectoryEventType;
    }

    public ArrayList<ExtendedProperty> getExtendedProperties() {
        return extendedProperties;
    }

    private void setExtendedProperties(ArrayList<ExtendedProperty> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }

    public String getClient() {
        return client;
    }

    private void setClient(String client) {
        this.client = client;
    }

    public long getLoginStatus() {
        return loginStatus;
    }

    private void setLoginStatus(long loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getUserDomain() {
        return userDomain;
    }

    private void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }

    public ArrayList<Info> getActor() {
        return actor;
    }

    private void setActor(ArrayList<Info> actor) {
        this.actor = actor;
    }

    public String getActorContextId() {
        return actorContextId;
    }

    private void setActorContextId(String actorContextId) {
        this.actorContextId = actorContextId;
    }

    public String getInterSystemsId() {
        return interSystemsId;
    }

    private void setInterSystemsId(String interSystemsId) {
        this.interSystemsId = interSystemsId;
    }

    public String getIntraSystemId() {
        return intraSystemId;
    }

    private void setIntraSystemId(String intraSystemId) {
        this.intraSystemId = intraSystemId;
    }

    public ArrayList<Info> getTarget() {
        return target;
    }

    private void setTarget(ArrayList<Info> target) {
        this.target = target;
    }

    public String getTargetContextId() {
        return targetContextId;
    }

    private void setTargetContextId(String targetContextId) {
        this.targetContextId = targetContextId;
    }
}
