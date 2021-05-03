DROP TABLE IF EXISTS buckets;
DROP TABLE IF EXISTS file_diffs;

CREATE TABLE file_diffs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  equal BOOLEAN,
  same_size BOOLEAN,
  diff_offset INT,
  diff_length INT
);

CREATE TABLE buckets (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  left_file BINARY,
  right_file BINARY,
  diff_id INT,
  UNIQUE KEY (name),
  FOREIGN KEY (diff_id) references file_diffs(id)
);
