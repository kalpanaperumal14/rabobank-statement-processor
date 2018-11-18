# Rabobank Customer Statement Processor

A **standalone spring-boot** java application with CommandLineRunner _which processes Rabo bank statement files_ (**csv** and **xml** formats).

It validates transaction records of the statement files and generate report with errors in the following 2 cases:

Case 1. Transaction records with _duplicate_ **Reference Number**.

Case 2. Transaction records with _Wrong_ **EndBalance**.


### Steps to run the application:

**Step 1**

Download the code and navigate to the project's base directory where pom.xml is. 
Execute the following maven command.

```
mvn clean install
```

**Step 2**

The above step would generate a jar under **target** directory.

```
cd target
Look for the presence of the jar **processor-0.0.1-SNAPSHOT.jar**
```

**Step 3**

You can **execute** the application as follows:

```
java -jar processor-0.0.1-SNAPSHOT.jar <path-to-statements-input-directory> <path-to-reports-output-directory>
```
_It needs 2 inputs to be supplied as command line arguments._

Input directory is mandatory which holds the statement files.

The reports will be generated in the output directory if provided, otherwise the reports will be stored in the input directory itself.

**Step 4** 

Log files will be generated in the folder processor_logs under current user's HOME directory .  


### Sample Input Files

**records.csv**

```csv
Reference,AccountNumber,Description,Start Balance,Mutation,End Balance
194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23
112806,NL27SNSB0917829871,Clothes for Willem Dekker,91.23,+15.57,106.8
183049,NL69ABNA0433647324,Clothes for Jan King,86.66,+44.5,131.16
183356,NL74ABNA0248990274,Subscription for Peter de Vries,92.98,-46.65,46.33
112806,NL69ABNA0433647324,Clothes for Richard de Vries,90.83,-10.91,79.92
112806,NL93ABNA0585619023,Tickets from Richard Bakker,102.12,+45.87,147.99
139524,NL43AEGO0773393871,Flowers from Jan Bakker,99.44,+41.23,140.67
179430,NL93ABNA0585619023,Clothes for Vincent Bakker,23.96,-27.43,-3.47
141223,NL93ABNA0585619023,Clothes from Erik Bakker,94.25,+41.6,135.85
195446,NL74ABNA0248990274,Flowers for Willem Dekker,26.32,+48.98,75.3
```

**records.xml**

```xml
<records>
  <record reference="130498">
    <accountNumber>NL69ABNA0433647324</accountNumber>
    <description>Tickets for Peter Theuß</description>
    <startBalance>26.9</startBalance>
    <mutation>-18.78</mutation>
    <endBalance>8.12</endBalance>
  </record>
  <record reference="167875">
    <accountNumber>NL93ABNA0585619023</accountNumber>
    <description>Tickets from Erik de Vries</description>
    <startBalance>5429</startBalance>
    <mutation>-939</mutation>
    <endBalance>6368</endBalance>
  </record>
  <record reference="147674">
    <accountNumber>NL93ABNA0585619023</accountNumber>
    <description>Subscription from Peter Dekker</description>
    <startBalance>74.69</startBalance>
    <mutation>-44.91</mutation>
    <endBalance>29.78</endBalance>
  </record>
  <record reference="135607">
    <accountNumber>NL27SNSB0917829871</accountNumber>
    <description>Subscription from Peter Theuß</description>
    <startBalance>70.42</startBalance>
    <mutation>+34.42</mutation>
    <endBalance>104.84</endBalance>
  </record>
  <record reference="169639">
    <accountNumber>NL43AEGO0773393871</accountNumber>
    <description>Tickets for Rik de Vries</description>
    <startBalance>31.78</startBalance>
    <mutation>-6.14</mutation>
    <endBalance>25.64</endBalance>
  </record>
  <record reference="105549">
    <accountNumber>NL43AEGO0773393871</accountNumber>
    <description>Flowers from Peter de Vries</description>
    <startBalance>105.75</startBalance>
    <mutation>-26.17</mutation>
    <endBalance>79.58</endBalance>
  </record>
  <record reference="150438">
    <accountNumber>NL74ABNA0248990274</accountNumber>
    <description>Tickets from Richard de Vries</description>
    <startBalance>10.1</startBalance>
    <mutation>-0.3</mutation>
    <endBalance>9.8</endBalance>
  </record>
  <record reference="172833">
    <accountNumber>NL69ABNA0433647324</accountNumber>
    <description>Tickets for Willem Theuß</description>
    <startBalance>66.72</startBalance>
    <mutation>-41.74</mutation>
    <endBalance>24.98</endBalance>
  </record>
  <record reference="165102">
    <accountNumber>NL93ABNA0585619023</accountNumber>
    <description>Tickets for Rik Theuß</description>
    <startBalance>3980</startBalance>
    <mutation>+1000</mutation>
    <endBalance>4981</endBalance>
  </record>
  <record reference="170148">
    <accountNumber>NL43AEGO0773393871</accountNumber>
    <description>Flowers for Jan Theuß</description>
    <startBalance>16.52</startBalance>
    <mutation>+43.09</mutation>
    <endBalance>59.61</endBalance>
  </record>
</records>
```

### Sample Ouput

_validation_report_2018-11-18_10-20-49.xml_

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<validationReport>
    <report>
        <fileName>e:\statements\records.csv</fileName>
        <validationResult>
            <result>
                <description>Duplicate transaction reference 112,806 for account number NL69ABNA0433647324</description>
                <reference>112806</reference>
            </result>
            <result>
                <description>Duplicate transaction reference 112,806 for account number NL93ABNA0585619023</description>
                <reference>112806</reference>
            </result>
        </validationResult>
    </report>
    <report>
        <fileName>e:\statements\records.xml</fileName>
        <validationResult>
            <result>
                <description>End balance is wrong. It should be 4,490 instead of 6,368</description>
                <reference>167875</reference>
            </result>
            <result>
                <description>End balance is wrong. It should be 4,980 instead of 4,981</description>
                <reference>165102</reference>
            </result>
        </validationResult>
    </report>
</validationReport>
```
