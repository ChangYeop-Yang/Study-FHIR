import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ContactPointDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Device;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.ContactPointUseEnum;
import ca.uhn.fhir.model.dstu2.valueset.DeviceStatusEnum;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Study1030 {

    public static void main(String[] args) {

        final Study1030 mStudy = new Study1030();

        final Device mDevice = new Device();
        mStudy.makeDevice(mDevice);
    }

    private void makeDevice(Device mDevice) {
        mDevice.addIdentifier().setSystem("http://acme.org/devices/serialnumber").setValue("1111");

        CodeableConceptDt mCodeableConceptDt = new CodeableConceptDt();
        mCodeableConceptDt.addCoding().setCode("528456").setDisplay("MDC_DEV_SPEC_PROFILE_AI_MED_MINDER").setSystem("urn:iso:std:iso:11073:10101");
        mDevice.setType(mCodeableConceptDt);

        mDevice.setStatus(DeviceStatusEnum.AVAILABLE);
        mDevice.setManufacturer("Healthall, Inc");
        mDevice.setModel("Cabinet0009");
        mDevice.setUdi(UUID.randomUUID().toString());

        mDevice.setLocation(new ResourceReferenceDt().setDisplay("Location/10811"));
        mDevice.setPatient(new ResourceReferenceDt().setDisplay("Patient/10810"));

        final List<ContactPointDt> mList = new LinkedList<ContactPointDt>();
        mList.add(new ContactPointDt().setValue("01045829311").setUse(ContactPointUseEnum.MOBILE));
        mDevice.setContact(mList);

        final String mString = FhirContext.forDstu2().newXmlParser().setPrettyPrint(true).encodeResourceToString(mDevice);
        System.out.println(mString);
    }

        private void makePatient(Patient mPatient) {

        }
}
