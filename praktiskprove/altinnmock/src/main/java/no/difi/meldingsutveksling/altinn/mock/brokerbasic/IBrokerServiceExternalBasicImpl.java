package no.difi.meldingsutveksling.altinn.mock.brokerbasic;

import javax.jws.WebService;

@WebService(targetNamespace = "http://www.altinn.no/services/ServiceEngine/Broker/2015/06", endpointInterface="no.difi.meldingsutveksling.altinn.mock.brokerbasic.IBrokerServiceExternalBasic", portName = "BasicHttpBinding_IBrokerServiceExternalBasic")
public class IBrokerServiceExternalBasicImpl implements IBrokerServiceExternalBasic {
    @Override
    public void test() throws IBrokerServiceExternalBasicTestAltinnFaultFaultFaultMessage {
    }

    @Override
    public String initiateBrokerServiceBasic(String systemUserName, String systemPassword, BrokerServiceInitiation brokerServiceInitiation) throws IBrokerServiceExternalBasicInitiateBrokerServiceBasicAltinnFaultFaultFaultMessage {
        return "123456789";
    }

    @Override
    public BrokerServiceAvailableFileList getAvailableFilesBasic(String systemUserName, String systemPassword, BrokerServiceSearch searchParameters) throws IBrokerServiceExternalBasicGetAvailableFilesBasicAltinnFaultFaultFaultMessage {
        ObjectFactory objectFactory = new ObjectFactory();
        BrokerServiceAvailableFileList result = objectFactory.createBrokerServiceAvailableFileList();
        BrokerServiceAvailableFile file = objectFactory.createBrokerServiceAvailableFile();
        file.setFileReference("somefilereference");
        result.getBrokerServiceAvailableFile().add(file);
        return result;
    }
}
