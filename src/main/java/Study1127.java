import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.ResourceMetadataKeyEnum;
import ca.uhn.fhir.model.base.resource.ResourceMetadataMap;
import ca.uhn.fhir.model.dstu2.composite.*;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.dstu2.valueset.ObservationStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.parser.StrictErrorHandler;
import ca.uhn.fhir.validation.*;
import ca.uhn.fhir.validation.schematron.SchematronBaseValidator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.hl7.fhir.instance.model.Resource;
import org.hl7.fhir.instance.model.api.IBaseResource;
import sun.plugin.util.UIUtil;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class Study1127 {

    public static void main(String[] args) {

        final Study1127 mStudy1127 = new Study1127();

        final Observation mObservation = new Observation();
        mStudy1127.makeObservation(mObservation);

        mStudy1127.vaildateprocess();
    }

    private void makeObservation(final Observation mObservation) {

        mObservation.setId("satO2");
        final ResourceMetadataMap mResourceMetadataMap = new ResourceMetadataMap();
        mResourceMetadataMap.put(ResourceMetadataKeyEnum.PROFILES, "http://hl7.org/fhir/StructureDefinition/vitalsigns");
        mObservation.setResourceMetadata(mResourceMetadataMap);

        final ArrayList<IdentifierDt> mList = new ArrayList<IdentifierDt>();
        mList.add(new IdentifierDt().setSystem("http://goodcare.org/observation/id"));
        mList.add(new IdentifierDt().setValue("o1223435-10"));
        mObservation.setIdentifier(mList);

        mObservation.setStatus(ObservationStatusEnum.FINAL);
        final CodeableConceptDt mCodeableConceptDt = new CodeableConceptDt();
        mCodeableConceptDt.addCoding(new CodingDt().setSystem("http://hl7.org/fhir/observation-category").setCode("vital-signs").setDisplay("Vital Signs"));
        mCodeableConceptDt.setText("Vital Signs");
        mObservation.setCategory(mCodeableConceptDt);

        mObservation.setCode(new CodeableConceptDt()
                .addCoding(new CodingDt().setSystem("http://loinc.org").setCode("59408-5").setDisplay("Oxygen saturation in Arterial blood by Pulse oximetry"))
                .addCoding(new CodingDt().setSystem("urn:iso:std:iso:11073:10101").setCode("150456").setDisplay("MDC_PULS_OXIM_SAT_O2"))
        );

        mObservation.setSubject(new ResourceReferenceDt().setReference("Patient/example"));
        mObservation.setEffective(new DateTimeDt().setValue(new Date()));
        mObservation.setValue(new QuantityDt().setValue(95).setUnit("%").setSystem("http://unitsofmeasure.org\"").setCode("%"));

        mObservation.setInterpretation(new CodeableConceptDt().addCoding(new CodingDt().setSystem("http://hl7.org/fhir/v2/0078").setCode("N").setDisplay("Normal")).setText("Normal (applies to non-numeric results)"));

        mObservation.setDevice(new ResourceReferenceDt().setReference("DeviceMetric/example"));

        final ArrayList<Observation.ReferenceRange> mRangeList = new ArrayList<Observation.ReferenceRange>();
        mRangeList.add(new Observation.ReferenceRange()
                .setLow((SimpleQuantityDt) new SimpleQuantityDt().setValue(90).setUnit("%").setSystem("http://unitsofmeasure.org").setCode("%"))
                .setHigh((SimpleQuantityDt) new SimpleQuantityDt().setValue(99).setUnit("%").setSystem("http://unitsofmeasure.org").setCode("%"))
        );
        mObservation.setReferenceRange(mRangeList);

        final String mString = FhirContext.forDstu2().newXmlParser().setPrettyPrint(true).encodeResourceToString(mObservation);
        System.out.println(mString);
    }

    private void vaildateprocess() {

        final FhirContext mFhirContext = FhirContext.forDstu2();
        mFhirContext.setParserErrorHandler(new StrictErrorHandler());

        final FhirValidator mFhirValidator = mFhirContext.newValidator();

        final IValidatorModule mIValidatorModule = new SchemaBaseValidator(mFhirContext);
        mFhirValidator.registerValidatorModule(mIValidatorModule);

            try {
                final String nextFileContent = IOUtils.toString(new FileReader("/Users/yeop/Downloads/MyPatient.structuredefinition.xml"));
                final IBaseResource resource = mFhirContext.newXmlParser().parseResource(nextFileContent);

                final ValidationResult result = mFhirValidator.validateWithResult(resource);

                if (result.isSuccessful()) {
                    System.out.printf("%s\n%s", result.isSuccessful(), nextFileContent);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
