Berlin Clock Kata
Berlin Clock Kata
The Berlin Clock (Mengenlehreclock or Berlin Uhr) is a clock that tells the time using a series of illuminated coloured blocks, as you can see in the picture for this project.

The top lamp blinks to show seconds- it is illuminated on even seconds and off on odd seconds.

The next two rows represent hours. The upper row represents 5 hour blocks and is made up of 4 red lamps. The lower row represents 1 hour blocks and is also made up of 4 red lamps.

The final two rows represent the minutes. The upper row represents 5 minute blocks, and is made up of 11 lamps- every third lamp is red, the rest are yellow. The bottom row represents 1 minute blocks, and is made up of 4 yellow lamps.

You can read more about the Berlin Clock on Wikipedia.

Feature 1 - Converting Digital Time to Berlin Time
So what we want first is a way to get a textual representation of a Berlin Clock time based on a digital time. This is so we can use this converter everywhere, all we have to do is hook up a frontend. We're going to be going over the clock row by row to make things clearer and ensure we get everything right first time.

Implement the Single Minutes Row
As a clock user
I want to be able to see single minutes
So that I can accurately tell the time down to the minute
Given I have started the converter
When I enter $time
Then $row is returned for the single minutes row
Time	Row
00:00:00	OOOO
23:59:59	YYYY
12:32:00	YYOO
12:34:00	YYYY
12:35:00	OOOO
Implement the Five Minutes Row
As a clock user
I want to be able to see five minutes
So that I can tell higher minute amounts more easily at a glance
Given I have started the converter
When I enter $time
Then $row is returned for the five minutes row
Time	Row
00:00:00	OOOOOOOOOOO
23:59:59	YYRYYRYYRYY
12:04:00	OOOOOOOOOOO
12:23:00	YYRYOOOOOOO
12:35:00	YYRYYRYOOOO

Implement the Single Hours Row
As a clock user
I want to be able to see single hours
So that I can tell what hour it is
Given I have started the converter
When I enter $time
Then $row is returned for the single hours row
Time	Row
00:00:00	OOOO
23:59:59	RRRO
02:04:00	RROO
08:23:00	RRRO
14:35:00	RRRR

Implement the Five Hours Row
As a clock user
I want to be able to see five hours
So that I can tell higher hour amounts more easily at a glance
Given I have started the converter
When I enter $time
Then $row is returned for the five hours row
Time	Row
00:00:00	OOOO
23:59:59	RRRR
02:04:00	OOOO
08:23:00	ROOO
16:35:00	RRRO

Implement the Seconds Lamp
As a clock user
I want to be able to see seconds passing
So that I can see if my clock is working at a glance
Given I have started the converter
When I enter $time
Then $lamp is returned for the seconds lamp
Time	Row
00:00:00	Y
23:59:59	O
Integrate the Entire Berlin Clock
As a clock user
I want to be able to see an entire clock
So that I can tell what time it is at a glance
Given I have started the converter
When I enter $time
Then $clock is returned
Time	Clock
00:00:00	YOOOOOOOOOOOOOOOOOOOOOOO
23:59:59	ORRRRRRROYYRYYRYYRYYYYYY
16:50:06	YRRROROOOYYRYYRYYRYOOOOO
11:37:01	ORROOROOOYYRYYRYOOOOYYOO



Feature 2 - Converting Berlin Time to Digital Time
The change to using Berlin Time has gone so well that we've decided to introduce it everywhere, from the clocks on the microwaves to the company-approved wristwatches. Unfortunately, people are having trouble quickly deciphering the current time which is having a detrimental effect on productivity. As such, we need to create a converter that takes a Berlin Time and returns a Digital Time.

Convert Berlin Time to Digital Time
As a Berlin Clock user
I want to be able to convert a Berlin Time to a Digital Time
So that I can tell what time it is more easily
Given I have started the converter
When I enter a $berlinTime
Then $digitalTime is returned
Clock	Time
YOOOOOOOOOOOOOOOOOOOOOOO	00:00:00
ORRRRRRROYYRYYRYYRYYYYYY	23:59:59
YRRROROOOYYRYYRYYRYOOOOO	16:50:06
ORROOROOOYYRYYRYOOOOYYOO	11:37:01
