USE password_manager ;
DROP procedure IF EXISTS change_stored_password ;
DROP procedure IF EXISTS change_login_password ;

DELIMITER $$
USE password_manager $$
CREATE PROCEDURE change_stored_password(inp_username TINYTEXT, new_password TINYTEXT)
BEGIN
UPDATE user_stored_credentials
SET password = new_password
WHERE username = inp_username
;
END $$
DELIMITER ;

DELIMITER $$
USE password_manager $$
CREATE PROCEDURE change_login_password(inp_username TINYTEXT, new_password TINYTEXT)
BEGIN
UPDATE password_manager_accounts
SET password = new_password
WHERE username = inp_username
;
END $$
DELIMITER ;
