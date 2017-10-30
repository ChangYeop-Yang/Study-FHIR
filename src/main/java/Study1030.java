import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.*;
import ca.uhn.fhir.model.dstu2.resource.Device;
import ca.uhn.fhir.model.dstu2.resource.DeviceComponent;
import ca.uhn.fhir.model.dstu2.resource.DeviceMetric;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.valueset.*;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.InstantDt;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Study1030 {

    public static void main(String[] args) {

        final Study1030 mStudy = new Study1030();

        final Device mDevice = new Device();
        mStudy.makeDevice(mDevice);

        final DeviceComponent mDeviceComponent = new DeviceComponent();
        mStudy.makeDeviceComponent(mDeviceComponent);

        final DeviceMetric mDeviceMetric = new DeviceMetric();
        mStudy.makeDeviceMetric(mDeviceMetric);

        final Observation mObservation = new Observation();
        mStudy.makeObservation(mObservation);
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

        final List<IdentifierDt> mLists = new ArrayList<IdentifierDt>();
        mLists.add(new IdentifierDt().setSystem("http://www.knu.ac.kr").setValue("bpm001"));
        mObservation.setIdentifier(mLists);

        mObservation.setStatus(ObservationStatusEnum.REGISTERED);
        mObservation.setCode(new CodeableConceptDt().addCoding(new CodingDt().setSystem("https://rtmms.nist.gov").setCode("150020")
            .setDisplay("MDC_PRESS_BLD_NONINV")));

        mObservation.setSubject(new ResourceReferenceDt().setReference(UUID.randomUUID().toString()));

        mObservation.setEffective(new DateTimeDt().setValue(new Date(System.currentTimeMillis())));
        mObservation.setBodySite(new CodeableConceptDt().addCoding(new CodingDt().setSystem("http://snomed.info/sct").setCode("368209003")));
        mObservation.setDevice(new ResourceReferenceDt().setReference(UUID.randomUUID().toString()));

        final List<Observation.Component> mComponentList = new ArrayList<Observation.Component>();
        mComponentList.add(new Observation.Component().setCode(new CodeableConceptDt().addCoding(
                new CodingDt().setSystem("https://rtmms.nist.gov").setCode("150021").setDisplay("MDC_PRESS_BLD_NONINV_SYS")))
                .setValue(new QuantityDt().setValue(120).setUnit("mm[Hg]")));
        mComponentList.add(new Observation.Component().setCode(new CodeableConceptDt()
                .addCoding(new CodingDt().setSystem("https://rtmms.nist.gov").setCode("150022").setDisplay("MDC_PRESS_BLD_NONINV_DIA")))
                .setValue(new QuantityDt().setValue(80).setUnit("mm[Hg]")));
        mComponentList.add(new Observation.Component().setCode(new CodeableConceptDt()
                .addCoding(new CodingDt().setSystem("https://rtmms.nist.gov").setCode("150023").setDisplay("MDC_PRESS_BLD_NONINV_MEAN")))
                .setValue(new QuantityDt().setValue(100).setUnit("mm[Hg]")));

        mObservation.setComponent(mComponentList);

        final String mString = FhirContext.forDstu2().newXmlParser().setPrettyPrint(true).encodeResourceToString(mObservation);
        System.out.println(mString);
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
