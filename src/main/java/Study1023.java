import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.instance.model.ContactPoint;
import org.hl7.fhir.instance.model.Enumerations;
import org.hl7.fhir.instance.model.HumanName;
import org.hl7.fhir.instance.model.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Study1023 {
    public static void main(String[] args) {

        Patient patient = new Patient();

        patient.addIdentifier().setSystem("http:www.knu.ac.kr")
                .setValue("KNU003");

        patient.addName().setUse(HumanName.NameUse.USUAL).setText("양창엽");

        patient.addTelecom().setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue("010-4582-9311").setUse(ContactPoint.ContactPointUse.HOME);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Date birth = null;
        try {
            birth = dateFormat.parse("19931111");
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        patient.setGender(Enumerations.AdministrativeGender.MALE);
        patient.setBirthDate(birth);

        FhirContext ctx = FhirContext.forDstu2Hl7Org();
        String patientString = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(patient);

        System.out.println(patientString);

    }
}
