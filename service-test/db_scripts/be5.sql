DELETE FROM session WHERE status > 0;
DELETE FROM user WHERE status > 0;
insert into user (email, status, plevel, salt, pword) values  ('apiGatewayTestLogin@uci.edu', 1, 5, 'da738806', '91bc292bc1fda96d6479ebaef223f06c287c281c33e95a82c8cf5a92cc920591b7aadacc2d8d145780f4de6d0a253e8e7fac2aff441b92dc55b3f4a4d6ced922');
insert into user (email, status, plevel, salt, pword) values  ('apiGatewayTest@uci.edu', 1, 5, 'e62b95aa', '7478293f00de145f62b70da8544fcd9928e12dfa4fa120d921b9aa37387851037d734996e221282a415b9aca45d6370cdec1ba0cae81ad5eaeb71684c53c37a0');
insert into session values ('5eb88e2dee72c28ed24c7239815d921a6e17e48c212f705b01f9140192b61347d30186a86085e72f2ea7bc71ee7715f3405ffbad746ebb520feda762da3e569a', 'apiGatewayTest@uci.edu', '1', now(), now(), '2025-08-01 02:59:02');
