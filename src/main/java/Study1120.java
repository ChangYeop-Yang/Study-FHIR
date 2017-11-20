import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.*;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.*;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.rest.client.api.IGenericClient;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Study1120 {

    public static class MyJFrame extends JFrame {
        JPanel contentpane = new JPanel();
        JPanel patientInfo = new JPanel();
        JPanel observationInfo = new JPanel();
        JPanel serverInfo = new JPanel();
        JTextField srverAddress = new JTextField("http://155.230.118.103:8080/fhir", 20);

        JLabel familyNameLabel = new JLabel("성");
        JLabel givenNameLabel = new JLabel("이름");
        JLabel phoneLabel = new JLabel("전화번호");
        JLabel birthDateLabel = new JLabel("생년월일");
        JLabel genderLabel = new JLabel("성별");
        JLabel bpSYSLabel = new JLabel("MDC_PRESS_BLD_NONINV_SYS");
        JLabel bpSYSUnitLabel = new JLabel("mm[Hg]");
        JLabel bpDIALabel = new JLabel("MDC_PRESS_BLD_NONINV_DIA");
        JLabel bpDIAUnitLabel = new JLabel("mm[Hg]");
        JLabel bpMEANLabel = new JLabel("MDC_PRESS_BLD_NONINV_MEAN");
        JLabel bpMEANUnitLabel = new JLabel("mm[Hg]");
        JLabel bpGlucoseLabel = new JLabel("MDC_PRESS_BLD_NONINY_GLUCOSE");
        JLabel bpGluLabel = new JLabel("mmol/L");

        JTextField familyName = new JTextField("홍", 5);
        JTextField givenName = new JTextField("길동", 7);
        JTextField phone = new JTextField("010-0000-0000", 10);
        JTextField birthDate = new JTextField("19990301", 10);

        JTextField bpSYS = new JTextField("70", 5);
        JTextField bpDIA = new JTextField("130", 5);
        JTextField bpMEAN = new JTextField("100", 5);
        JTextField bpGLUCOSE = new JTextField("100", 5);

        JRadioButton male = new JRadioButton("남자");
        JRadioButton female = new JRadioButton("여자");

        JButton button = new JButton("전송");

        public MyJFrame() {
            setBounds(800, 280, 550, 500);
            setName("KNU Client");

            patientInfo.setPreferredSize(new Dimension(250, 210));
            patientInfo.setBorder(new TitledBorder(null, "환자정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));

            JPanel name = new JPanel();
            name.setPreferredSize(new Dimension(240,60));
            name.add(familyNameLabel);
            name.add(familyName);
            name.add(givenNameLabel);
            name.add(givenName);
            name.add(phoneLabel);
            name.add(phone);
            patientInfo.add(name);

            final JPanel birth = new JPanel();
            birth.setPreferredSize(new Dimension(240, 50));
            birth.add(birthDateLabel);
            birth.add(birthDate);
            patientInfo.add(birth);

            patientInfo.add(genderLabel);
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(male);
            buttonGroup.add(female);
            male.setSelected(true);
            patientInfo.add(male);
            patientInfo.add(female);
            contentpane.add(patientInfo);

            observationInfo.setPreferredSize(new Dimension(250, 250));
            observationInfo.setBorder(new TitledBorder(null, "측정값", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            observationInfo.add(bpSYSLabel);
            observationInfo.add(bpSYS);
            observationInfo.add(bpSYSUnitLabel);
            observationInfo.add(bpDIALabel);
            observationInfo.add(bpDIA);
            observationInfo.add(bpDIAUnitLabel);
            observationInfo.add(bpMEANLabel);
            observationInfo.add(bpMEAN);
            observationInfo.add(bpMEANUnitLabel);
            observationInfo.add(bpGlucoseLabel);
            observationInfo.add(bpGLUCOSE);
            observationInfo.add(bpGluLabel);
            contentpane.add(observationInfo);

            serverInfo.setPreferredSize(new Dimension(510, 70));
            serverInfo.setBorder(new TitledBorder(null, "서버주소", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            serverInfo.add(srverAddress, CENTER_ALIGNMENT);
            contentpane.add(serverInfo);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    final Study1120 mStudy = new Study1120();

                    final Bundle mBundle = new Bundle();
                    mBundle.setType(BundleTypeEnum.TRANSACTION);

                    final Observation mObservation = new Observation();
                    mStudy.makeObservation(mObservation, Integer.valueOf(bpGLUCOSE.getText()), Integer.valueOf(bpSYS.getText()),
                            Integer.valueOf(bpDIA.getText()), Integer.valueOf(bpMEAN.getText()), mBundle);

                    final Patient mPatient = new Patient();
                    final String sGender = male.isSelected() ? "남자" : "여자";
                    mStudy.makePatient(mBundle, mPatient, phone.getText(), birthDate.getText(), sGender, givenName.getText(), familyName.getText());

                    mStudy.sendServer(mBundle, srverAddress.getText());
                }
            });

            contentpane.add(button);
            setContentPane(contentpane);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);
       }
    }

    public static void main(String[] args) {
        final MyJFrame mFrame = new MyJFrame();
    }

    private void makeObservation(final Observation mObservation, final int mGlo, final int mSys, final int mDial, final int mMEAN, final Bundle mBundle) {
        mObservation.setId("f001");

        ArrayList<IdentifierDt> mList = new ArrayList<IdentifierDt>(10);
        mList.add(new IdentifierDt().setUse(IdentifierUseEnum.OFFICIAL));
        mList.add(new IdentifierDt().setSystem("http://www.bmc.nl/zorgportal/identifiers/observations"));
        mList.add(new IdentifierDt().setValue("6323"));
        mObservation.setIdentifier(mList);

        mObservation.setStatus(ObservationStatusEnum.FINAL);

        mObservation.setCode(new CodeableConceptDt().addCoding(new CodingDt().setSystem("http://loinc.org").setCode("15074-8").setDisplay("Glucose [Moles/volume] in Blood")));
        mObservation.setSubject(new ResourceReferenceDt().setReference("Patient001").setDisplay("P. van de Heuvel"));
        mObservation.setEffective(new PeriodDt().setStart((DateTimeDt) new DateTimeDt().setValue(new Date())));
        mObservation.setIssued((InstantDt) new InstantDt().setValue(new Date()));

        ArrayList<ResourceReferenceDt> mResourceReferenceDtList = new ArrayList<ResourceReferenceDt>(10);
        mResourceReferenceDtList.add(new ResourceReferenceDt().setReference("Practitioner/f005").setDisplay("A. Langeveld"));
        mObservation.setPerformer(mResourceReferenceDtList);

        mObservation.addComponent().setValue(new QuantityDt().setValue(mGlo).setUnit("mmol/l").setSystem("http://unitsofmeasure.org").setCode("mmol/L"));
        mObservation.addComponent().setValue(new QuantityDt().setValue(mSys).setUnit("mm[Hg]").setSystem("http://unitsofmeasure.org").setCode("mm[Hg]"));
        mObservation.addComponent().setValue(new QuantityDt().setValue(mDial).setUnit("mm[Hg]").setSystem("http://unitsofmeasure.org").setCode("mm[Hg]"));
        mObservation.addComponent().setValue(new QuantityDt().setValue(mMEAN).setUnit("mm[Hg]").setSystem("http://unitsofmeasure.org").setCode("mm[Hg]"));

        mBundle.addEntry().setFullUrl("urn:uuid:" + UUID.randomUUID().toString()).setResource(mObservation).getRequest()
                .setUrl("Observation").setMethod(HTTPVerbEnum.POST);
    }

    private void makePatient(final Bundle mBundle, final Patient patient, final String sTel, final String sBirthDay, final String sGender, final String sGiven, final String sFamily) {

        patient.addIdentifier().setSystem("http:www.knu.ac.kr")
                .setValue("KNU003");

        patient.addName().addGiven(sGiven).addFamily(sFamily).setUse(NameUseEnum.USUAL);

        patient.addTelecom().setSystem(ContactPointSystemEnum.PHONE)
                .setValue(sTel).setUse(ContactPointUseEnum.HOME);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Date birth = null;
        try {
            birth = dateFormat.parse(sBirthDay);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (sGender.equals("남자")) { patient.setGender(AdministrativeGenderEnum.MALE); }
        else { patient.setGender(AdministrativeGenderEnum.FEMALE); }

        patient.setBirthDate((DateDt) new DateDt().setValue(birth));

        mBundle.addEntry().setFullUrl("urn:uuid:" + UUID.randomUUID().toString()).setResource(patient).getRequest()
                .setUrl("Patient").setMethod(HTTPVerbEnum.POST);
    }

    private void sendServer(final Bundle mBundle, final String sURL) {

        FhirContext ctx = FhirContext.forDstu2();
        String bundleString = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(mBundle);
        System.out.println(bundleString);

        final IGenericClient mClient = FhirContext.forDstu2().newRestfulGenericClient(sURL);
        mClient.create().resource(mBundle).prettyPrint().encodedXml().execute();
    }
}
