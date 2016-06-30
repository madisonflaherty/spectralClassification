# Give the number of data points as an argument and then it will grep each url (per line) in 
# the file and put it in its own file in the 'raw_data_files' folder
if (( $# != 1)); then
  echo "Usage: ./get_data.sh <number of data points desired>"
  exit 1
fi
TOTAL=$1

if [ -d "raw_data_files" ]; then
  rm -r raw_data_files
fi
mkdir raw_data_files
COUNT=1
while read -r line ||  [[ -n "$line" ]]; do
  wget $line -P raw_data_files > /dev/null 2>&1
  if (( COUNT >= $TOTAL)) ; then
    echo "complete!"
    break
  else
    COUNT=$((COUNT+1))
    echo -ne "."
  fi
done < "init_data_urls_10000.txt"
