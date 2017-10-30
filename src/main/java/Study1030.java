import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.model.dstu2.composite.*;
import ca.uhn.fhir.model.dstu2.resource.*;
import ca.uhn.fhir.model.dstu2.valueset.ContactPointUseEnum;
import ca.uhn.fhir.model.dstu2.valueset.DeviceMetricCategoryEnum;
import ca.uhn.fhir.model.dstu2.valueset.DeviceMetricOperationalStatusEnum;
import ca.uhn.fhir.model.dstu2.valueset.DeviceStatusEnum;
import ca.uhn.fhir.model.primitive.BaseDateTimeDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import net.sf.saxon.functions.CurrentDateTime;

import java.text.SimpleDateFormat;
import java.util.*;

public class Study1030 {

    public static void main(String[] args) {

        final Study1030 mStudy = new Study1030();

        final Device mDevice = new Device();
        mStudy.makeDevice(mDevice);

        final Observation mObservation = new Observation();
        mStudy.makeObservation(mObservation);

        final DeviceComponent mDeviceComponent = new DeviceComponent();
        mStudy.makeDeviceComponent(mDeviceComponent);

        final DeviceMetric mDeviceMetric = new DeviceMetric();
        mStudy.makeDeviceMetric(mDeviceMetric);
    }

    private void makeDevice(final Device mDevice) {
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

        final List<ContactPointDt> mList = new ArrayList<ContactPointDt>();
        mList.add(new ContactPointDt().setValue("01045829311").setUse(ContactPointUseEnum.MOBILE));
        mDevice.setContact(mList);

        final String mString = FhirContext.forDstu2().newXmlParser().setPrettyPrint(true).encodeResourceToString(mDevice);
        System.out.println(mString);
    }

    private void makeObservation(final Observation mObservation) {

    }

    private void makeDeviceMetric(final DeviceMetric mDeviceMetric) {
        mDeviceMetric.setType(new CodeableConceptDt().addCoding(new CodingDt().setSystem("https://rtmms.nist.gov").setCode("150020").setDisplay("MDC_PRESS_BLD_NONINV")));
        mDeviceMetric.setIdentifier(new IdentifierDt().setSystem("http://www.vanilla.kr").setValue("metric001"));

        mDeviceMetric.setUnit(new CodeableConceptDt().addCoding(new CodingDt().setSystem("https://rtmms.nist.gov").setCode("266016").setDisplay("MDC_DIM_MMHG")));
        mDeviceMetric.setSource(new ResourceReferenceDt().setReference(UUID.randomUUID().toString()));
        mDeviceMetric.setParent(new ResourceReferenceDt().setReference(UUID.randomUUID().toString()));
        mDeviceMetric.setOperationalStatus(DeviceMetricOperationalStatusEnum.ON);
        mDeviceMetric.setCategory(DeviceMetricCategoryEnum.MEASUREMENT);

        final String mString = FhirContext.forDstu2().newXmlParser().setPrettyPrint(true).encodeResourceToString(mDeviceMetric);
        System.out.println(mString);
    }

    private void makeDeviceComponent(final DeviceComponent mDeviceComponent) {
        final CodeableConceptDt mCodeableConceptDt = new CodeableConceptDt();
        mCodeableConceptDt.addCoding().setSystem("urn:iso:std:iso:11073:10101").setCode("4173").setDisplay("MDC_DEV_ANALY_PRESS_BLD_MDS");
        mDeviceComponent.setType(mCodeableConceptDt);

        mDeviceComponent.setIdentifier(new IdentifierDt().setSystem("http://www.vanilla.co.kr").setValue("mds001"));
        mDeviceComponent.setLastSystemChange((InstantDt) new InstantDt().setValue(Calendar.getInstance().getTime()));
        mDeviceComponent.setSource(new ResourceReferenceDt().setReference(UUID.randomUUID().toString()));

        final List<CodeableConceptDt> mLists = new ArrayList<CodeableConceptDt>();
        mLists.add(new CodeableConceptDt().addCoding(new CodingDt().setCode("on")));
        mDeviceComponent.setOperationalStatus(mLists);

        final String mString = FhirContext.forDstu2().newXmlParser().setPrettyPrint(true).encodeResourceToString(mDeviceComponent);
        System.out.println(mString);
    }
}
