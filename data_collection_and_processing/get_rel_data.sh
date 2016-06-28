shopt -s nullglob
FILES=raw_data_files/*.fits
for f in $FILES
do
  printf "Processing $f file...  "
  python getRelData.py $f
  if [ $? -eq 0 ]; then
    echo SUCCESS
  else
    echo FAILED to process $f
  fi
done 

