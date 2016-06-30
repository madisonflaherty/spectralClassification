# helper functions
exit_on_error ()
{
  echo $1
  exit 1
}

N=10
while test ${#} != 0
do 

    case "$1" in
      --n) shift; N=$1;;
      *) ;;
    esac
    shift
done

echo "Collecting $N star samples..."


# script
./get_data.sh $N && echo "Successfully gathered data from urls..." || exit_on_error "Failed to gather data from urls!"
./get_rel_data.sh $N && echo "Successfully collected relevant data from total data..." || exit_on_error "Unable to parse collected data!"
python filterData.py && echo "Successfully filtered data" || exit_on_error "Failed to filter data"
python combineCVSData.py && echo "Succesfully combined data" || exit_on_error "Failed to combine all data"

