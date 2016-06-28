# Give a filename as an argument and then it will grep each url (per line) in 
# the file and put it in its own file in the 'raw_data_files' folder
mkdir raw_data_files
while read -r line ||  [[ -n "$line" ]]; do
  wget $line -P raw_data_files
done < "$1"
