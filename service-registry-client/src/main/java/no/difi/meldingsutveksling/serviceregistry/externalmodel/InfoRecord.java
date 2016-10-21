package no.difi.meldingsutveksling.serviceregistry.externalmodel;

import com.google.common.base.MoreObjects;

/**
 *
 */
public class InfoRecord {
    private String primaryServiceIdentifier;
    private String identifier;
    private String organizationName;
    private EntityType entityType;

    public InfoRecord(String primaryServiceIdentifier, String identifier, String organizationName, EntityType
            entityType) {
        this.primaryServiceIdentifier = primaryServiceIdentifier;
        this.identifier = identifier;
        this.organizationName = organizationName;
        this.entityType = entityType;
    }

    /** Needed by gson **/
    public InfoRecord() {
    }

    public String getPrimaryServiceIdentifier() {
        return primaryServiceIdentifier;
    }

    public void setPrimaryServiceIdentifier(String primaryServiceIdentifier) {
        this.primaryServiceIdentifier = primaryServiceIdentifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("primaryServiceIdentifier", primaryServiceIdentifier)
                .add("identifier", identifier)
                .add("organizationName", organizationName)
                .add("entityType", entityType)
                .toString();
    }
}