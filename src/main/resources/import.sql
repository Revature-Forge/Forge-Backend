--Two users
INSERT INTO users (ID, ADMIN, EMAIL, F_NAME, L_NAME, PASSWORD) VALUES (2, FALSE,'test@gmail.com', 'Qel', 'Mirialan', 'password');
INSERT INTO users (ID, ADMIN, EMAIL, F_NAME, L_NAME, PASSWORD) VALUES (1, FALSE ,'testUser@test.com', 'FirstName', 'LastName', 'password');

--PORTFOLIOS 
INSERT INTO PORTFOLIOS (ID, APPROVED, FEEDBACK, NAME, REVIEWED, SUBMITTED, USER_ID) VALUES (1, FALSE, NULL,	'Your Porfolio', FALSE,	FALSE, 1 );

--About me
INSERT INTO ABOUT_ME (ID, PORTFOLIO_ID, BIO, EMAIL, PHONE) VALUES (1, 1,'Lorem superpos�s valise pourparlers r�ver chiots rendez-vous naissance Eiffel myrtille. Gr�ves Arc de Triomphe encore pourquoi sentiments baguette p�diluve une projet sentiments saperlipopette vachement le. Brume �ph�m�re baguette Bordeaux en fait sommet avoir minitel.\n\nNous avoir parole la nous moussant. Superpos�s tatillon exprimer voler St Emilion ressemblant �ph�m�re bourguignon. Bourguignon penser c�lin mill�sime peripherique annoncer enfants enfants vachement nuit formidable encombr� �panoui chiots. Arc truc cacato�s lorem fl�ner.', 'maga@trumpsucks.org', '1234567890' );

--Certification
INSERT INTO CERTIFICATION (CERTIFICATIONID, CERT_ID, ISSUED_BY, ISSUED_ON, NAME, PUBLIC_URL, PORTFOLIO_ID) VALUES (1, '123458442DIS', 'University of Toronto', '2020-10-11', 'TEFL-C', '' ,1);

--Education
INSERT INTO EDUCATION (ID, "DEGREE", GPA , GRADUATION_DATE, LOGO_URL, UNIVERSITY ,PORTFOLIO_ID) VALUES (1, 'BA French', '3.54', '2011-05-06', '', 'Austin Peay State University', 1 );

--EQUIVALENCIES
INSERT INTO EQUIVALENCIES (ID, HEADER,  VALUE, PORTFOLIO_ID ) VALUES (1, 'Java', 13, 1 );
INSERT INTO EQUIVALENCIES (ID, HEADER,  VALUE, PORTFOLIO_ID ) VALUES (2, 'SQL', 14, 1 );
INSERT INTO EQUIVALENCIES (ID, HEADER,  VALUE, PORTFOLIO_ID ) VALUES (3, 'JavaScript', 8, 1);
INSERT INTO EQUIVALENCIES (ID, HEADER,  VALUE, PORTFOLIO_ID ) VALUES (4, 'Spring Boot', 6 , 1 );
INSERT INTO EQUIVALENCIES (ID, HEADER,  VALUE, PORTFOLIO_ID ) VALUES (5, 'Spring Framework', 6, 1 );

--GitHub
INSERT INTO GITHUB (ID, IMAGE, URL,	PORTFOLIO_ID ) VALUES (1, '', '' , 1);

--HONORS
INSERT INTO HONORS (ID, DATE_RECEIVED, DESCRIPTION, RECEIVED_FROM, TITLE, PORTFOLIO_ID) VALUES (1, '2020-10-11', 'Tutor of the Month', 'Research Foundation York College' , 'Tutor of the Month', 1 );

