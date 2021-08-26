Introduction to the JIRC project
================================

JIRC is a Java based client for Internet Relay Chat.
Started in 2004 as a school project, JIRC had the purpose of getting into Java and embrace IRC which was much more popular in those days. Finally we decided to put this on github for beeing preserved for the future.

https://en.wikipedia.org/wiki/Internet_Relay_Chat


Building instructions
=====================

The here provided solution is a netbeans project which was build with netbeans 12.4. Once pulled, the project should be opened and compiled with netbeans without any further configuration or requiremnts.

Client Configuration
=============

JIRC is capable of handling several connections to IRC servers. It is required to have a config.xml in the root directory from where jirc can read the server configurations.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <server id="ircconnection">
    <address>server.irc.local/address>
    <port>6667</port>
    <nick>nick</nick>
    <host>localhost</host>
    <server>ircserver</server>
    <rlname>RealName</rlname>
  </server>
</configuration>
```

Address and port a crucial for establishing a connection successfully. 

Bug Reports
===========

For the moment no future development is planned, however there may be little improvmenets from time to time.


JDOM license information
=========================

JDOM is used and inclued in the JIRC source. 
https://github.com/hunterhacker/jdom

 Copyright (C) 2000-2012 Jason Hunter & Brett McLaughlin.
 All rights reserved.
 
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.
 
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows 
    these conditions in the documentation and/or other materials 
    provided with the distribution.

 3. The name "JDOM" must not be used to endorse or promote products
    derived from this software without prior written permission.  For
    written permission, please contact <request_AT_jdom_DOT_org>.
 
 4. Products derived from this software may not be called "JDOM", nor
    may "JDOM" appear in their name, without prior written permission
    from the JDOM Project Management <request_AT_jdom_DOT_org>.
 
 In addition, we request (but do not require) that you include in the 
 end-user documentation provided with the redistribution and/or in the 
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by the
      JDOM Project (http://www.jdom.org/)."
 Alternatively, the acknowledgment may be graphical using the logos 
 available at http://www.jdom.org/images/logos.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 This software consists of voluntary contributions made by many 
 individuals on behalf of the JDOM Project and was originally 
 created by Jason Hunter <jhunter_AT_jdom_DOT_org> and
 Brett McLaughlin <brett_AT_jdom_DOT_org>.  For more information
 on the JDOM Project, please see <http://www.jdom.org/>. 

