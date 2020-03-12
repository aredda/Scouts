<h2>Scouts</h2>
<p>It is a demo mobile application that aims to facilitate management of a particular part of the Moroccan Scouts system.</p>

<h2>Development Tools</h2>
<p>This is an <b>Android</b> application, I've used Android Studio to develop it, this was my first time working on an Android project,</p>
<p>not to mention, that I didn't implement SQL to save data, this was just a demo project, I decided to go with Binary Serialization.</p>

<h2>Structure</h2>
<p>For the Java source files, I divided the project into four main packages:<p/>
<h3>- Models</h3>
<ul>
  <li><b>Norms</b><i> (basically a package of Enums)</i>
    <ul>
      <li>ECourse</li>
      <li>EGender</li>
      <li>EMission</li>
      <li>EUnitType</li>
    </ul>
  </li>
  <li><b>Staff</b>
    <ul>
      <li>Person<i> Abstract Class</i></li>
      <li>Adherent</li>
      <li>Member</li>
      <li>Leader</li>
      <li>Parent</li>
      <li>User</li>
    </ul>
  </li>
  <li><b>System</b>
    <ul>
      <li>Arrangement<i> Abstract Class</i></li>
      <li>Group</li>
      <li>Unit</li>
      <li>Mission</li>
      <li>Enrollment</li>
      <li>Badge</li>
    </ul>
  </li>
  <li><b>DataController</b></li>
  <li><b>Entity</b></li>
</ul>
<h3>- Helpers</h3>
<ul>
  <li>HelperMessage</li>
  <li>HelperReflection</li>
  <li>HelperResource</li>
</ul>
<h3>- Adapters</h3>
<ul>
  <li>TriAdapter</li>
  <li>FlexAdapter</li>
</ul>
<h3>- Activities</h3>
<ul>
  <li>Home</li>
  <li>Authentication</li>
  <li>GroupInsert</li>
  <li>GroupList</li>
  <li>GroupUnitList</li>
  <li>LeaderInsert</li>
  <li>LeaderDetails</li>
  <li>LeaderList</li>
  <li>MemberInsert</li>
  <li>MemberDetails</li>
  <li>MemberList</li>
  <li>UnitInsert</li>
  <li>UnitDetails</li>
</ul>

<h2>Screenshots</h2>
<p>Here are some screenshots taken from the app, there are still a lot of activities to cover, those are just a few:</p>
<h5>Authentication Activity:</h5>
<img src='https://i.imgur.com/tKlvkgh.png'/>
<h5>Main Menu Activity:</h5>
<img src='https://i.imgur.com/GHgPSVW.png'/>
<h5>List of leaders Activity:</h5>
<img src='https://i.imgur.com/ZwbzJFf.png'/>
<h5>List of all members Activity:</h5>
<img src='https://i.imgur.com/oKAOiMH.png'/>
<h5>A particular type of unit Activity:</h5>
<img src='https://i.imgur.com/UcsKRMn.png' />
<h5>A member/leader details Activity:</h5>
<img src='https://i.imgur.com/suRVyjP.png' />
