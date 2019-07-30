# Paddy-Power-Coding-Assignment
Converter that validates and reformats match times.

### What the app does

This is a command line app that reads strings from a file, which represent a match time, and either reformats the string or determines it to be INVALID, based on the formats specified below. The file needs to be passed in a command line argument. Instructions are in the last section of this README.

In order to be a valid match time.the format needs to be as follows:</br>
**[period] minutes:seconds.milliseconds**

The output is in this format:</br>
**normalTimeMinutes:normalTimeSeconds - period**

For the output format, minutes should be padded to two digits and milliseconds should be rounded 
up or down to the nearest whole second. Periods are represented in short-form on the input format and long-form 
on output format i.e.

<table style="width:200%">
  <tr>
    <th>Short Form</th>
    <th>Long Form</th> 
  </tr>
  <tr>
    <td>PM</td>
    <td>PRE_MATCH</td> 
  </tr>
  <tr>
    <td>H1</td>
    <td>FIRST_HALF</td> 
  </tr>
  <tr>
    <td>HT</td>
    <td>HALF_TIME</td> 
  </tr>
  <tr>
    <td>H2</td>
    <td>SECOND_HALF</td> 
  </tr>
  <tr>
    <td>FT</td>
    <td>FULL_TIME</td> 
  </tr>
</table>

When a given period goes into additional time (i.e. > 45:00.000 for first half, > 90.00.000 for the second half), the added minutes and seconds are represented separately in the format.

**normalTimeMinutes:normalTimeSeconds +additionalMinutes:additionalSeconds - period**

Any input which does not meet the required input format should result in an output of INVALID

#### Examples

<table style="width:200%">
  <tr>
    <th>Input</th>
    <th>Expected Output</th> 
  </tr>
  <tr>
    <td>"[PM] 0:00.000"</td>
    <td>"00:00 - PRE_MATCH"</td> 
  </tr>
  <tr>
    <td>"[H1] 0:15.025</td>
    <td>"00:15 - FIRST_HALF"</td> 
  </tr>
  <tr>
    <td>"[H1] 3:07.513"</td>
    <td>"03:08 – FIRST_HALF"</td> 
  </tr>
  <tr>
    <td>"[H1] 45:00.001"</td>
    <td>"45:00 +00:00 – FIRST_HALF"</td> 
  </tr>
  <tr>
    <td>"[H1] 46:15.752"</td>
    <td>"45:00 +01:16 – FIRST_HALF"</td> 
  </tr>
  <tr>
    <td>"[HT] 45:00.000"</td>
    <td>"45:00 – HALF_TIME"</td> 
  </tr>
  <tr>
    <td>"[H2] 45:00.500"</td>
    <td>"45:01 – SECOND_HALF"</td> 
  </tr>
  <tr>
    <td>"[H2] 90:00.908"</td>
    <td>"90:00 +00:01 – SECOND_HALF"</td> 
  </tr>
  <tr>
    <td>"[FT] 90:00.000"</td>
    <td>"90:00 – FULL_TIME"</td> 
  </tr>
  <tr>
    <td>"90:00"</td>
    <td>"INVALID"</td> 
  </tr>
  <tr>
    <td>"[H3] 90:00.000"</td>
    <td>"INVALID"</td> 
  </tr>
  <tr>
    <td>"[PM] -10:00.000"</td>
    <td>"INVALID"</td> 
  </tr>
  <tr>
    <td>"FOO"</td>
    <td>"INVALID"</td> 
  </tr>
</table>


### Instructions

 - Download the source code to your local machine.
 - Using your terminal, cd into the java folder. It will look like this **localLocation/paddypowertest/src/main/java**
 - Compile the program. javac Main.java
 - From inside the same folder, run the program by passing the input file as the first and only argument.
 - eg **java Main /locationOfData/inputs Jr Java Dev coding assessment.data**
